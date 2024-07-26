package com.storehousemgm.purchaseorder.headerfooter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable header = new PdfPTable(1);
        PdfPTable footer = new PdfPTable(1);

        try {
            // Header
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setBorder(0);
            header.addCell(new PdfPCell(new Phrase("Ecommerce Shopping Application")));

            // Footer
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setBorder(0);
            footer.addCell(new PdfPCell(new Phrase("Contact us: Bangalore, India")));

            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            footer.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        }

//    @Override
//    public void onEndPage(PdfWriter writer, Document document) {
//        PdfPTable header = new PdfPTable(1);
//        PdfPTable footer = new PdfPTable(1);
//
//        try {
//            // header
//            header.setTotalWidth(527);
//            header.setLockedWidth(true);
//            header.getDefaultCell().setBorder(0);
//            header.addCell(new PdfPCell(new Phrase("Ecommerce Shopping Application")));
//
//            //  footer
//            footer.setTotalWidth(527);
//            footer.setLockedWidth(true);
//            footer.getDefaultCell().setBorder(0);
//            footer.addCell(new PdfPCell(new Phrase("contact us : Bengalore, India")));
//
//            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
//            footer.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
//        } catch (DocumentException e) {
//            throw new ExceptionConverter(e);
//        }
    }
}
