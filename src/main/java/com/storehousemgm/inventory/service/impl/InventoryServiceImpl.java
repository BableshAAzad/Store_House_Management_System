package com.storehousemgm.inventory.service.impl;

import com.storehousemgm.client.entity.Client;
import com.storehousemgm.client.repository.ClientRepository;
import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.exception.*;
import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.dto.InventorySearchCriteria;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.inventory.mapper.InventoryMapper;
import com.storehousemgm.inventory.repository.InventoryRepository;
import com.storehousemgm.inventory.service.InventoryService;
import com.storehousemgm.inventory.specification.InventorySpecification;
import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.stock.mapper.StockMapper;
import com.storehousemgm.stock.repository.StockRepository;
import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockMapper stockMapper;

    //--------------------------------------------------------------------------------------------------------------------

    private PagedModel.PageMetadata getPageMetadata(Page<?> page) {
        return new PagedModel.PageMetadata(
                page.getSize(),
                page.getNumber(),
                page.getTotalElements()
        );
    }

    private <T> PagedModel<T> getPagedModel(Page<T> page) {
        return PagedModel.of(page.getContent(), getPageMetadata(page));
    }

    private ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> buildResponse(Page<InventoryResponse> inventoryResponsePage, String message) {
        PagedModel<InventoryResponse> pagedModel = getPagedModel(inventoryResponsePage);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<PagedModel<InventoryResponse>>()
                .setStatus(HttpStatus.OK.value())
                .setMessage(message)
                .setData(pagedModel));
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Inventory> inventoryPage = inventoryRepository.findAll(pageable);
        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
        return buildResponse(inventoryResponsePage, "Inventories are Found");
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventoriesBySellerId(Long sellerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Inventory> inventoryPage = inventoryRepository.findBySellerId(sellerId, pageable);
        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
        return buildResponse(inventoryResponsePage, "Inventories are Found");
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> filterInventories(InventorySearchCriteria searchCriteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Inventory> spec = InventorySpecification.getSpecification(searchCriteria);
        Page<Inventory> inventoryPage = inventoryRepository.findAll(spec, pageable);
        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
        return buildResponse(inventoryResponsePage, "Inventories are Found");
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> searchInventories(String decodedCriteria, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Inventory> spec = InventorySpecification.hasSearchCriteria(decodedCriteria);
        Page<Inventory> inventoryPage = inventoryRepository.findAll(spec, pageable);
        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
        return buildResponse(inventoryResponsePage, "Inventories are Found");
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<InventoryResponse>> addInventory(
            InventoryRequest inventoryRequest, Long storageId, Long clientId, int quantity) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotExistException("ClientId : " + clientId + ", is not exist"));

        Storage storage = storageRepository.findById(storageId).orElseThrow(() -> new StorageNotExistException("StorageId : " + storageId + ", is not exist"));
        Inventory inventory = inventoryMapper.mapInventoryRequestToInventory(inventoryRequest, new Inventory());
        inventory.setRestockedAt(LocalDate.now());

        double productSize = inventory.getBreadthInMeters() * inventory.getHeightInMeters() * inventory.getLengthInMeters();
        double updatedStorageArea = storage.getAvailableArea() - (productSize * quantity);
        if (updatedStorageArea < 0)
            throw new IllegalOperationException("Insufficient space in storage");
        else
            storage.setAvailableArea(updatedStorageArea);

        double updatedStorageMaxWeight = storage.getMaxAdditionalWeightInKg() - (inventory.getWeightInKg() * quantity);
        if (updatedStorageMaxWeight < 0)
            throw new IllegalOperationException("Weight is too much, not support storage");
        else
            storage.setMaxAdditionalWeightInKg(updatedStorageMaxWeight);

        Set<MaterialType> inventoryMaterialTypes = inventory.getMaterialTypes();
        List<MaterialType> storageMaterialTypes = storage.getMaterialTypes();
        if (!new HashSet<>(storageMaterialTypes).containsAll(inventoryMaterialTypes))
            throw new IllegalOperationException("Material types are not match with storage materials");

        inventory.setClient(client);
        storage.setSellerId(inventory.getSellerId());
        storage = storageRepository.save(storage);
        inventory.setStorages(List.of(storage));

        inventory = inventoryRepository.save(inventory);

        Stock stock = new Stock();
        stock.setQuantity(quantity);
        stock.setStorage(storage);
        stock.setInventory(inventory);
        stock = stockRepository.save(stock);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<InventoryResponse>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("Inventory Created")
                .setData(inventoryMapper.mapInventoryToInventoryResponse(inventory, stock)));
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(InventoryRequest inventoryRequest, Long inventoryId) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            List<Storage> listStorages = getUpdatedStorages(inventory, inventoryRequest);
            inventory = inventoryMapper.mapInventoryRequestToInventory(inventoryRequest, inventory);
            if (inventoryRequest.getMaterialTypes().isEmpty()) {
                inventory.setMaterialTypes(inventory.getMaterialTypes());
            } else {
                Set<MaterialType> newMaterialType = new HashSet<>(inventoryRequest.getMaterialTypes());
                inventory.setMaterialTypes(newMaterialType);
            }
            inventory.setUpdatedInventoryAt(LocalDate.now());
            inventory.setStorages(listStorages);
            inventory = inventoryRepository.save(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<InventoryResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("Inventory Updated")
                    .setData(inventoryMapper.mapInventoryToInventoryResponse(inventory)));
        }).orElseThrow(() -> new InventoryNotExistException("InventoryId : " + inventoryId + ", is not exist"));
    }
    //--------------------------------------------------------------------------------------------------------------------

    private static List<Storage> getUpdatedStorages(Inventory inventory, InventoryRequest inventoryRequest) {
        double requestProductSize = inventoryRequest.getBreadthInMeters() * inventoryRequest.getHeightInMeters() * inventoryRequest.getLengthInMeters();
        double existProductSize = inventory.getBreadthInMeters() * inventory.getHeightInMeters() * inventory.getLengthInMeters();
        double qnt = inventory.getStocks().getFirst().getQuantity();

        double reqMaxWeight = inventoryRequest.getWeightInKg() * qnt;
        double availableMaxWeight = inventory.getWeightInKg() * qnt;

        List<Storage> listStorages = inventory.getStorages();
        listStorages.forEach(storage -> {
            double updatedStorageArea = storage.getAvailableArea() - ((requestProductSize - existProductSize) * qnt);
            if (updatedStorageArea < 0)
                throw new IllegalOperationException("Insufficient space in storage");
            else
                storage.setAvailableArea(updatedStorageArea);

            double updatedStorageMaxWeight = storage.getMaxAdditionalWeightInKg() - (reqMaxWeight - availableMaxWeight);
            if (updatedStorageMaxWeight < 0)
                throw new IllegalOperationException("Weight is too much, not support storage");
            else
                storage.setMaxAdditionalWeightInKg(updatedStorageMaxWeight);

            Set<MaterialType> inventoryMaterialTypes = inventory.getMaterialTypes();
            List<MaterialType> storageMaterialTypes = storage.getMaterialTypes();
            if (!new HashSet<>(storageMaterialTypes).containsAll(inventoryMaterialTypes))
                throw new IllegalOperationException("Material types are not match with storage materials");
        });
        return listStorages;
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<InventoryResponse>> findInventory(Long inventoryId) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<InventoryResponse>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("Inventory Founded")
                    .setData(inventoryMapper.mapInventoryToInventoryResponse(inventory)));
        }).orElseThrow(() -> new InventoryNotExistException("InventoryId : " + inventoryId + ", is not exist"));
    }

    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<StockResponse>> updateStock(StockRequest stockRequest, Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotExistException("StockId : " + stockId + ", is not exist"));

        Storage storage = getUpdatedStorage(stock, stockRequest);
        storage = storageRepository.save(storage);
        stock.setQuantity(stockRequest.getQuantity());
        stock.setStorage(storage);
        stock = stockRepository.save(stock);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<StockResponse>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Stock Updated")
                .setData(stockMapper.mapStockToStockResponse(stock)));
    }
    //--------------------------------------------------------------------------------------------------------------------

    private static Storage getUpdatedStorage(Stock stock, StockRequest stockRequest) {
        Storage storage = stock.getStorage();
        Inventory inventory = stock.getInventory();

        double existingArea = inventory.getLengthInMeters() * inventory.getHeightInMeters() * inventory.getBreadthInMeters();
        double existingWeight = inventory.getWeightInKg();
        double availableStorageArea = storage.getAvailableArea();
        double availableStorageWeight = storage.getMaxAdditionalWeightInKg();

        if (stock.getQuantity() < stockRequest.getQuantity()) {
            availableStorageArea -= (existingArea * stockRequest.getQuantity() - existingArea * stock.getQuantity());
            availableStorageWeight -= (existingWeight * stockRequest.getQuantity() - existingWeight * stock.getQuantity());
        } else {
            availableStorageArea += (existingArea * stock.getQuantity() - existingArea * stockRequest.getQuantity());
            availableStorageWeight += (existingWeight * stock.getQuantity() - existingWeight * stockRequest.getQuantity());
        }

        if (availableStorageArea < 0) {
            throw new IllegalOperationException("Insufficient space in storage");
        } else
            storage.setAvailableArea(availableStorageArea);


        if (availableStorageWeight < 0)
            throw new IllegalOperationException("Insufficient weight in storage");
        else
            storage.setMaxAdditionalWeightInKg(availableStorageWeight);

        return storage;
    }
    //--------------------------------------------------------------------------------------------------------------------

//    @Override
//    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventoriesBySellerId(
//            Long sellerId, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Inventory> inventoryPage = inventoryRepository.findBySellerId(sellerId, pageable);
//        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
//
//        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
//                inventoryResponsePage.getSize(),
//                inventoryResponsePage.getNumber(),
//                inventoryResponsePage.getTotalElements()
//        );
//        PagedModel<InventoryResponse> pagedModel = PagedModel.of(inventoryResponsePage.getContent(), pageMetadata);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<PagedModel<InventoryResponse>>()
//                .setStatus(HttpStatus.OK.value())
//                .setMessage("Inventories are Found")
//                .setData(pagedModel));
//    }

    //--------------------------------------------------------------------------------------------------------------------
//    @Override
//    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> filterInventories(
//            InventorySearchCriteria searchCriteria, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Inventory> inventoryPage = inventoryRepository.findAll(InventorySpecification.getSpecification(searchCriteria), pageable);
//        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
//
//        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
//                inventoryResponsePage.getSize(),
//                inventoryResponsePage.getNumber(),
//                inventoryResponsePage.getTotalElements()
//        );
//        PagedModel<InventoryResponse> pagedModel = PagedModel.of(inventoryResponsePage.getContent(), pageMetadata);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<PagedModel<InventoryResponse>>()
//                .setStatus(HttpStatus.OK.value())
//                .setMessage("Inventories are Found")
//                .setData(pagedModel));
//    }

    //--------------------------------------------------------------------------------------------------------------------

//    @Override
//    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> searchInventories(
//            String decodedCriteria, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Specification<Inventory> spec = InventorySpecification.hasSearchCriteria(decodedCriteria);
//        Page<Inventory> inventoryPage = inventoryRepository.findAll(spec, pageable);
//        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
//
//        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
//                inventoryResponsePage.getSize(),
//                inventoryResponsePage.getNumber(),
//                inventoryResponsePage.getTotalElements()
//        );
//        PagedModel<InventoryResponse> pagedModel = PagedModel.of(inventoryResponsePage.getContent(), pageMetadata);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<PagedModel<InventoryResponse>>()
//                .setStatus(HttpStatus.OK.value())
//                .setMessage("Inventories are Found")
//                .setData(pagedModel));
//    }
    //--------------------------------------------------------------------------------------------------------------------
//    @Override
//    public ResponseEntity<ResponseStructure<PagedModel<InventoryResponse>>> findInventories(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Inventory> inventoryPage = inventoryRepository.findAll(pageable);
//        Page<InventoryResponse> inventoryResponsePage = inventoryPage.map(inventoryMapper::mapInventoryToInventoryResponse);
//
//        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(
//                inventoryResponsePage.getSize(),
//                inventoryResponsePage.getNumber(),
//                inventoryResponsePage.getTotalElements()
//        );
//        PagedModel<InventoryResponse> pagedModel = PagedModel.of(inventoryResponsePage.getContent(), pageMetadata);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<PagedModel<InventoryResponse>>()
//                .setStatus(HttpStatus.OK.value())
//                .setMessage("Inventories are Found")
//                .setData(pagedModel));
//    }
    //--------------------------------------------------------------------------------------------------------------------

}
