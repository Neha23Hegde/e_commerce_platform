package com.ecommerce.util;

import com.ecommerce.dto.OrderItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {

    public static String generateInvoice(int orderId, String userName, String address,
                                         String paymentMethod, double totalAmount,
                                         List<OrderItem> items) {
        String filePath = null;
        try {
            // Get Tomcat base directory 
            String baseDir = System.getProperty("catalina.base") + File.separator + "invoices";

            // Ensure folder exists
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            //  Build file path
            filePath = baseDir + File.separator + "Order_" + orderId + "_Invoice.pdf";

            // Start PDF generation
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Title
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Order Invoice", boldFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Order Info
            document.add(new Paragraph("Order ID: " + orderId));
            document.add(new Paragraph("Customer: " + userName));
            document.add(new Paragraph("Address: " + address));
            document.add(new Paragraph("Payment Method: " + paymentMethod));
            document.add(new Paragraph(" "));

            // Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 1, 2, 2});

            table.addCell("Product");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Total");
            //fetching the details from OrderItem dto class
            for (OrderItem item : items) {
                table.addCell(item.getProductName()); 
                table.addCell(String.valueOf(item.getQuantity())); 
                table.addCell(String.valueOf(item.getPrice())); 
                table.addCell(String.valueOf(item.getTotalPrice()));
            }
            
            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Amount: " + totalAmount, boldFont));

            document.close();
            System.out.println("PDF Invoice generated: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
}
