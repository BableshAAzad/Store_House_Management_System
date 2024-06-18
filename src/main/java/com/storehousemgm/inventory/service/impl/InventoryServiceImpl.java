package com.storehousemgm.inventory.service.impl;

import com.storehousemgm.client.entity.Client;
import com.storehousemgm.client.repository.ClientRepository;
import com.storehousemgm.exception.ClientNotExistException;
import com.storehousemgm.exception.InventoryNotExistException;
import com.storehousemgm.exception.StorageNotExistException;
import com.storehousemgm.inventory.dto.InventoryRequest;
import com.storehousemgm.inventory.dto.InventoryResponse;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.inventory.mapper.InventoryMapper;
import com.storehousemgm.inventory.repository.InventoryRepository;
import com.storehousemgm.inventory.service.InventoryService;
import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<InventoryResponse>> addInventory(
            InventoryRequest inventoryRequest, Long storageId, Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ClientNotExistException("ClientId : " + clientId + ", is not exist"));

        return storageRepository.findById(storageId).map(storage -> {
            Inventory inventory = inventoryMapper.mapInventoryRequestToInventory(inventoryRequest, new Inventory());
            inventory.setRestockedAt(LocalDate.now());

            double productSize = inventory.getBreadthInMeters() * inventory.getHeightInMeters() * inventory.getLengthInMeters();
            double updatedStorageArea = storage.getAvailableArea() - (productSize * inventory.getQuantity());
            storage.setAvailableArea(updatedStorageArea);

            double updatedStorageMaxWeight = storage.getMaxAdditionalWeightInKg() - (inventory.getWeightInKg() * inventory.getQuantity());
            storage.setMaxAdditionalWeightInKg(updatedStorageMaxWeight);

            inventory.setClient(client);
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
        double qnt = inventory.getQuantity();

        double maxWeight = inventory.getWeightInKg() * inventory.getQuantity();

        List<Storage> listStorages = inventory.getStorages();
        listStorages.forEach(storage -> {
            double updatedStorageArea = storage.getAvailableArea() - (productSize * qnt);
            storage.setAvailableArea(updatedStorageArea);

            double updatedStorageMaxWeight = storage.getMaxAdditionalWeightInKg() - maxWeight;
            storage.setMaxAdditionalWeightInKg(updatedStorageMaxWeight);
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
