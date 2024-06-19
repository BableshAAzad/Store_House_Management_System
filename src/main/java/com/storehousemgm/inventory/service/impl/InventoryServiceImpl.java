package com.storehousemgm.inventory.service.impl;

import com.storehousemgm.client.entity.Client;
import com.storehousemgm.client.repository.ClientRepository;
import com.storehousemgm.enums.MaterialType;
import com.storehousemgm.exception.ClientNotExistException;
import com.storehousemgm.exception.IllegalOperationException;
import com.storehousemgm.exception.InventoryNotExistException;
import com.storehousemgm.exception.StorageNotExistException;
import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.inventory.mapper.InventoryMapper;
import com.storehousemgm.inventory.repository.InventoryRepository;
import com.storehousemgm.inventory.service.InventoryService;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.stock.repository.StockRepository;
import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<InventoryResponse>> addInventory(
            InventoryRequest inventoryRequest, Long storageId, Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotExistException("ClientId : " + clientId + ", is not exist"));

        return storageRepository.findById(storageId).map(storage -> {
            Inventory inventory = inventoryMapper.mapInventoryRequestToInventory(inventoryRequest, new Inventory());
            inventory.setRestockedAt(LocalDate.now());

            Optional<Stock> optionalStock = stockRepository.findByQuantity(inventoryRequest.getQuantity());
            Stock stock;
            if (optionalStock.isEmpty()) {
                stock = new Stock();
                stock.setQuantity(inventoryRequest.getQuantity());
                stock.setStorage(storage);
                stock = stockRepository.save(stock);
                inventory.setStocks(List.of(stock));
            } else {
                stock = optionalStock.get();
                inventory.setStocks(List.of(stock));
            }

            double productSize = inventory.getBreadthInMeters() * inventory.getHeightInMeters() * inventory.getLengthInMeters();
            double updatedStorageArea = storage.getAvailableArea() - (productSize * inventoryRequest.getQuantity());
            if (updatedStorageArea <= 0)
                throw new IllegalOperationException("Sufficient space in storage");
            else
                storage.setAvailableArea(updatedStorageArea);

            double updatedStorageMaxWeight = storage.getMaxAdditionalWeightInKg() - (inventory.getWeightInKg() * inventoryRequest.getQuantity());
            if (updatedStorageMaxWeight <= 0)
                throw new IllegalOperationException("Weight is too much, not support storage");
            else
                storage.setMaxAdditionalWeightInKg(updatedStorageMaxWeight);

            List<MaterialType> inventoryMaterialTypes = inventory.getMaterialTypes();
            List<MaterialType> storageMaterialTypes = storage.getMaterialTypes();
            if (!new HashSet<>(storageMaterialTypes).containsAll(inventoryMaterialTypes))
                throw new IllegalOperationException("Material types are not match with storage materials");

            inventory.setClient(client);
            storage.setSellerId(inventory.getSellerId());
            storage = storageRepository.save(storage);
            inventory.setStorages(List.of(storage));

            inventory = inventoryRepository.save(inventory);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<InventoryResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("Inventory Created")
                    .setData(inventoryMapper.mapInventoryToInventoryResponse(inventory)));
        }).orElseThrow(() -> new StorageNotExistException("StorageId : " + storageId + ", is not exist"));
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<InventoryResponse>> updateInventory(InventoryRequest inventoryRequest, Long inventoryId) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            inventory = inventoryMapper.mapInventoryRequestToInventory(inventoryRequest, inventory);
            inventory.setRestockedAt(LocalDate.now());

            List<Storage> listStorages = getUpdatedStorages(inventory);
            inventory.setStorages(listStorages);
            inventory = inventoryRepository.save(inventory);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<InventoryResponse>()
                    .setStatus(HttpStatus.CREATED.value())
                    .setMessage("Inventory Updated")
                    .setData(inventoryMapper.mapInventoryToInventoryResponse(inventory)));
        }).orElseThrow(() -> new InventoryNotExistException("InventoryId : " + inventoryId + ", is not exist"));
    }

    private static List<Storage> getUpdatedStorages(Inventory inventory) {
        double productSize = inventory.getBreadthInMeters() * inventory.getHeightInMeters() * inventory.getLengthInMeters();
        double qnt = inventory.getStocks().getFirst().getQuantity();

        double maxWeight = inventory.getWeightInKg() * inventory.getStocks().getFirst().getQuantity();

        List<Storage> listStorages = inventory.getStorages();
        listStorages.forEach(storage -> {
            double updatedStorageArea = storage.getAvailableArea() - (productSize * qnt);
            if (updatedStorageArea <= 0)
                throw new IllegalOperationException("Sufficient space in storage");
            else
                storage.setAvailableArea(updatedStorageArea);

            double updatedStorageMaxWeight = storage.getMaxAdditionalWeightInKg() - maxWeight;
            if (updatedStorageMaxWeight <= 0)
                throw new IllegalOperationException("Weight is too much, not support storage");
            else
                storage.setMaxAdditionalWeightInKg(updatedStorageMaxWeight);

            List<MaterialType> inventoryMaterialTypes = inventory.getMaterialTypes();
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
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<InventoryResponse>()
                    .setStatus(HttpStatus.FOUND.value())
                    .setMessage("Inventory Founded")
                    .setData(inventoryMapper.mapInventoryToInventoryResponse(inventory)));
        }).orElseThrow(() -> new InventoryNotExistException("InventoryId : " + inventoryId + ", is not exist"));
    }

    //--------------------------------------------------------------------------------------------------------------------
    @Override
    public ResponseEntity<ResponseStructure<List<InventoryResponse>>> findInventories() {
        List<InventoryResponse> inventoryResponses = inventoryRepository
                .findAll()
                .stream()
                .map(inventory -> inventoryMapper.mapInventoryToInventoryResponse(inventory))
                .toList();
        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseStructure<List<InventoryResponse>>()
                .setStatus(HttpStatus.FOUND.value())
                .setMessage("Inventories are Founded")
                .setData(inventoryResponses));
    }
    //--------------------------------------------------------------------------------------------------------------------

}
