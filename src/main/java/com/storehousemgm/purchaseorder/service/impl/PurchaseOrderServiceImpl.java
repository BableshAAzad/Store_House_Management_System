package com.storehousemgm.purchaseorder.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.storehousemgm.exception.InventoryNotExistException;
import com.storehousemgm.exception.PurchaseOrderNotCompletedException;
import com.storehousemgm.exception.PurchaseOrderNotExistException;
import com.storehousemgm.exception.StockNotExistException;
import com.storehousemgm.inventory.entity.Inventory;
import com.storehousemgm.inventory.repository.InventoryRepository;
import com.storehousemgm.purchaseorder.entity.OrderInvoice;
import com.storehousemgm.purchaseorder.dto.OrderRequestDto;
import com.storehousemgm.purchaseorder.dto.OrderResponseDto;
import com.storehousemgm.purchaseorder.entity.PurchaseOrder;
import com.storehousemgm.purchaseorder.headerfooter.HeaderFooterPageEvent;
import com.storehousemgm.purchaseorder.mapper.PurchaseOrderMapper;
import com.storehousemgm.purchaseorder.repository.OrderInvoiceRepository;
import com.storehousemgm.purchaseorder.repository.PurchaseOrderRepository;
import com.storehousemgm.purchaseorder.service.PurchaseOrderService;
import com.storehousemgm.stock.entity.Stock;
import com.storehousemgm.stock.repository.StockRepository;
import com.storehousemgm.utility.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private OrderInvoiceRepository orderInvoiceRepository;
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<OrderResponseDto>> findPurchaseOrder(Long orderId) {
        return purchaseOrderRepository.findById(orderId).map(purchaseOrder -> {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<OrderResponseDto>()
                    .setStatus(HttpStatus.OK.value())
                    .setMessage("PurchaseOrder Founded")
                    .setData(purchaseOrderMapper.mapPurchaseOrderToOrderResponse(purchaseOrder)));
        }).orElseThrow(() -> new PurchaseOrderNotExistException("OrderId : " + orderId + ", is not exist"));
    }
    //--------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<ResponseStructure<List<OrderResponseDto>>> findPurchaseOrders(Long customerId) {
        List<OrderResponseDto> listPurchaseOrders = purchaseOrderRepository
                .findByCustomerId(customerId)
                .stream()
                .map(purchaseOrder -> purchaseOrderMapper.mapPurchaseOrderToOrderResponse(purchaseOrder))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<List<OrderResponseDto>>()
                .setStatus(HttpStatus.OK.value())
                .setMessage("PurchaseOrders are Founded")
                .setData(listPurchaseOrders));
    }

    @Override
    public ResponseEntity<ResponseStructure<OrderResponseDto>> generatePurchaseOrder(OrderRequestDto orderRequestDto, Long inventoryId) throws IOException {
        Inventory inventory = inventoryRepository
                .findById(inventoryId)
                .orElseThrow(() -> new InventoryNotExistException("InventoryId : " + inventoryId + ", is not exist"));
        Stock stock = stockRepository
                .findByInventory(inventory)
                .orElseThrow(() -> new StockNotExistException("Stock not exist...!!!"));

        PurchaseOrder purchaseOrder = null;
        if (stock.getQuantity() >= orderRequestDto.getTotalQuantity()) {

            stock.setQuantity(stock.getQuantity() - orderRequestDto.getTotalQuantity());
            stockRepository.save(stock);

            // Generate the PDF and save it
            byte[] pdfData = createPdf(orderRequestDto);

            purchaseOrder = PurchaseOrder.builder()
                    .orderId(orderRequestDto.getOrderId())
                    .invoiceLink("/clients/purchase-orders/invoice/" + UUID.randomUUID().toString())
                    .orderQuantity(orderRequestDto.getTotalQuantity())
                    .customerId(orderRequestDto.getCustomerId())
                    .invoiceDate(LocalDate.now())
                    .inventories(List.of(inventory))
                    .build();
            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

            // Save PDF data to the database
            OrderInvoice orderInvoice = OrderInvoice.builder()
                    .orderId(purchaseOrder.getOrderId())
                    .pdfData(pdfData)
                    .build();
            orderInvoiceRepository.save(orderInvoice);
        } else {
            throw new PurchaseOrderNotCompletedException("Insufficient Order quantities");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<OrderResponseDto>()
                .setStatus(HttpStatus.CREATED.value())
                .setMessage("PurchaseOrder Created")
                .setData(OrderResponseDto.builder()
                        .orderId(purchaseOrder.getOrderId())
                        .inventoryTitle(inventory.getProductTitle())
                        .inventoryImage(inventory.getProductImage())
                        .invoiceLink(purchaseOrder.getInvoiceLink())
                        .invoiceDate(purchaseOrder.getInvoiceDate())
                        .build()));
    }

    @Override
    public byte[] getPdfData(Long orderId) {
        OrderInvoice orderInvoice = orderInvoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PurchaseOrderNotExistException("OrderId : " + orderId + ", is not exist"));
        return orderInvoice.getPdfData();
    }

    //--------------------------------------------------------------------------------------------------------------------
    public byte[] createPdf(OrderRequestDto orderRequestDto) throws DocumentException, IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);

//        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
//        writer.setPageEvent(event);
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
        Paragraph titleParagraph = new Paragraph("Ecommerce Shopping Application", titleFont);
        titleParagraph.setAlignment(Paragraph.ALIGN_CENTER); // Center align the text
        titleParagraph.setSpacingAfter(20f); // Add 20 points of space after the paragraph
        document.add(titleParagraph);

        // Create a table for invoice details
        PdfPTable table = new PdfPTable(2); // 2 columns

        // Table headers
        PdfPCell cell = new PdfPCell(new Phrase("Invoice Details"));
        cell.setColspan(2);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        table.addCell(cell);

        // Order Id
        table.addCell("Order Id:");
        table.addCell(String.valueOf(orderRequestDto.getOrderId()));

        // Customer Id
        table.addCell("Customer Id:");
        table.addCell(String.valueOf(orderRequestDto.getCustomerId()));

        // Total Quantity
        table.addCell("Total Quantity:");
        table.addCell(String.valueOf(orderRequestDto.getTotalQuantity()));

        // Total Price
        table.addCell("Total Price:");
        table.addCell(String.valueOf(orderRequestDto.getTotalPrice()));

        // Discount Price
        table.addCell("Discount Price:");
        table.addCell(String.valueOf(orderRequestDto.getDiscountPrice()));

        // Total Payable Amount
        table.addCell("Total Payable Amount:");
        table.addCell(String.valueOf(orderRequestDto.getTotalPayableAmount()));

        // Address
        table.addCell("Address:");
        table.addCell(
                "Street: " + orderRequestDto.getAddressDto().getStreetAddress() + "\n" +
                        "Street Additional: " + orderRequestDto.getAddressDto().getStreetAddressAdditional() + "\n" +
                        "City: " + orderRequestDto.getAddressDto().getCity() + "\n" +
                        "State: " + orderRequestDto.getAddressDto().getState() + "\n" +
                        "Country: " + orderRequestDto.getAddressDto().getCountry() + "\n" +
                        "Pincode: " + orderRequestDto.getAddressDto().getPincode()
        );

        // Contact Numbers
        table.addCell("Primary Contact:");
        table.addCell(orderRequestDto.getAddressDto().getContactNumber1());

        table.addCell("Secondary Contact:");
        table.addCell(orderRequestDto.getAddressDto().getContactNumber2());

        // Add the table to the document
        document.add(table);
        document.close();
        return byteArrayOutputStream.toByteArray();
    }


//        String title = "Ecommerce Shopping Application";
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
//
//        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
//        writer.setPageEvent(event);
//        document.open();
//
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
//        Paragraph titleParagraph = new Paragraph(title, titleFont);
//        document.add(titleParagraph);
//
//        Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
//        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
//
//        document.add(new Paragraph("Order Id : " + orderRequestDto.getOrderId(), paraFont));
//        document.add(new Paragraph("Customer Id : " + orderRequestDto.getCustomerId(), paraFont));
//        document.add(new Paragraph("Total Quantity : " + orderRequestDto.getTotalQuantity(), paraFont));
//        document.add(new Paragraph("Total Price : " + orderRequestDto.getTotalPrice(), paraFont));
//        document.add(new Paragraph("Discount Price : " + orderRequestDto.getDiscountPrice(), paraFont));
//        document.add(new Paragraph("Total Payable Amount = " + orderRequestDto.getTotalPayableAmount(), boldFont));
//
//        document.add(new Paragraph("Address : ", boldFont));
//        document.add(new Paragraph(
//                ", Street : " + orderRequestDto.getAddressDto().getStreetAddress() +
//                        ", Area : " + orderRequestDto.getAddressDto().getStreetAddressAdditional() +
//                        ", City : " + orderRequestDto.getAddressDto().getCity() +
//                        ", State : " + orderRequestDto.getAddressDto().getState() +
//                        ", Country : " + orderRequestDto.getAddressDto().getCountry() +
//                        ", Pincode : " + orderRequestDto.getAddressDto().getPincode(), paraFont));
//
//        document.add(new Paragraph("Primary Contact : " +
//                orderRequestDto.getAddressDto().getContactNumber1(), boldFont));
//        document.add(new Paragraph("Secondary Contact : " +
//                orderRequestDto.getAddressDto().getContactNumber2(), paraFont));
//
//        document.close();
//        return byteArrayOutputStream.toByteArray();
//}


}
