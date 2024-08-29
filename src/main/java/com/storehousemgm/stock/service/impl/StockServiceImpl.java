package com.storehousemgm.stock.service.impl;

import com.storehousemgm.exception.IllegalOperationException;
import com.storehousemgm.exception.StockNotExistException;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.stock.dto.StockRequest;
import com.storehousemgm.stock.dto.StockResponse;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.stock.mapper.StockMapper;
import com.storehousemgm.stock.repository.StockRepository;
import com.storehousemgm.stock.service.StockService;
import com.storehousemgm.storage.entity.Storage;
import com.storehousemgm.storage.repository.StorageRepository;
import com.storehousemgm.utility.ResponseStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;
    private final StorageRepository storageRepository;

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
    @Override
    public ResponseEntity<ResponseStructure<StockResponse>> getStock(Long stockId) {
       Stock stock = stockRepository.findById(stockId)
                .orElseThrow(()->new StockNotExistException("Stock Id : "+stockId+", is not exist"));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<StockResponse>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Stock Updated")
                .setData(stockMapper.mapStockToStockResponse(stock)));
    }

    //--------------------------------------------------------------------------------------------------------------------


    //--------------------------------------------------------------------------------------------------------------------


}
