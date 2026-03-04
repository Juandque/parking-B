package org.park.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import org.park.model.entities.ParkingOccupancy;
import org.park.model.entities.Payment;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class TicketService {
    public byte[] generateEntryTicket(ParkingOccupancy parkingOccupancy){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Rectangle pageSize = new  Rectangle(164,500);
        Document document = new Document(pageSize, 10,10,10,10);
        try {
            PdfWriter writer=PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Fuentes
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            // Contenido del Ticket
            Paragraph titulo = new Paragraph("PARKING " + "MiJuli", boldFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("--------------------------------", normalFont));
            document.add(new Paragraph("TICKET DE ENTRADA", boldFont));
            document.add(new Paragraph("ID: " + parkingOccupancy.getId(), normalFont));
            document.add(new Paragraph("Placa: " + parkingOccupancy.getVehicle().getLicensePlate().toUpperCase(), boldFont));
            document.add(new Paragraph("Puesto: " + parkingOccupancy.getParkingSlot().getNumber(), normalFont));

            document.add(new Paragraph("Fecha: " + parkingOccupancy.getOccupationStartDate(), normalFont));
            document.add(new Paragraph("--------------------------------", normalFont));

            PdfContentByte cb = writer.getDirectContent();
            Barcode128 barcode128 = generateBarcode(parkingOccupancy);

            Image code128Image = barcode128.createImageWithBarcode(cb, null, null);
            code128Image.setAlignment(Element.ALIGN_CENTER);

            Paragraph footer = new Paragraph("Conserve este ticket\npara su salida.", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        }catch(DocumentException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generatePaymentTicket(Payment payment){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Rectangle pageSize = new  Rectangle(164,500);
        Document document = new Document(pageSize, 10,10,10,10);
        ParkingOccupancy parkingOccupancy = payment.getParkingOccupancy();
        try{
            PdfWriter writer=PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            document.add(new Paragraph("--------------------------------", normalFont));
            document.add(new Paragraph("TICKET DE PAGO", boldFont));
            document.add(new Paragraph("ID: " + parkingOccupancy.getId(), normalFont));
            document.add(new Paragraph("Placa: " + parkingOccupancy.getVehicle().getLicensePlate().toUpperCase(), boldFont));
            document.add(new Paragraph("Puesto: " + parkingOccupancy.getParkingSlot().getNumber(), normalFont));

            document.add(new Paragraph("Fecha Entrada: " + parkingOccupancy.getOccupationStartDate(), normalFont));
            document.add(new Paragraph("Fecha Salida: " + parkingOccupancy.getOccupationEndDate(), normalFont));
            document.add(new Paragraph("--------------------------------", normalFont));

            document.add(new Paragraph("Valor: " + payment.getTotalAmount(), boldFont));
            document.add(new Paragraph("Fecha: " + payment.getPaymentDate(), normalFont));
            document.add(new Paragraph("Método de Pago" + payment.getPaymentMethod(), boldFont));

            Paragraph footer = new Paragraph("Gracias por elegir a\nParking MiJuli.", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
        }catch(DocumentException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    private Barcode128 generateBarcode(ParkingOccupancy parkingOccupancy){
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(parkingOccupancy.getId().toString());
        barcode128.setCodeType(Barcode128.CODE128);
        return barcode128;
    }
}
