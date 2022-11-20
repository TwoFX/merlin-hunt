package de.markushimmel.merlinhunt.immortalgame;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;

@Path("/internal/gen")
public class GenerateSecretRestService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String generateSecret() throws Exception {
        SecretGenerator secretGenerator = new DefaultSecretGenerator(64);
        String secret = secretGenerator.generate();

        QrData data = new QrData.Builder()
                .label("Immortal Game")
                .secret(secret)
                .issuer("Merlin Hunt")
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(6)
                .period(30)
                .build();

        var writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(data.getUri(), BarcodeFormat.QR_CODE, 0, 0);

        StringBuilder result = new StringBuilder();
        result.append(secret);
        result.append('\n');

        for (int i = 0; i < bitMatrix.getWidth(); i++) {
            for (int j = 0; j < bitMatrix.getHeight(); j++) {
                result.append(bitMatrix.get(i, j) ? '!' : '?');
            }
        }

        return result.toString();
    }
}
