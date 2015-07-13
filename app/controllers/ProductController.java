package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ProductDTO;
import models.Image;
import models.Product;
import models.ProductCategory;
import play.Logger;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;
import sapsan.common.UploadUtils;
import utils.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andy on 13.07.15.
 */
public class ProductController extends Controller {

    public static Result createProducts() {
        JsonNode json = request().body().asJson();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<ProductDTO> products = mapper.readValue(json.toString(), new TypeReference<List<ProductDTO>>() {
            });
            products.stream().forEach(ProductController::createProduct);
        } catch (IOException e) {
            Logger.warn("error while parsing create products request:" + json);
            return badRequest();
        }

        return created();
    }

    private static void createProduct(ProductDTO productDTO) {
        boolean exists = Product.hasProductWithName(productDTO.name);
        if (exists) {
            Logger.warn("product with name " + productDTO.name + "already exists");
            return;
        }
        ProductCategory category = ProductCategory.findByName(productDTO.category);
        if (category == null) {
            Logger.warn("category with name " + productDTO.category + "not exists");
            return;
        }

        Product newProduct = new Product();
        newProduct.name = productDTO.name.trim();
        newProduct.articul = productDTO.articul;
        newProduct.category = category;
        newProduct.createdAt = Calendar.getInstance().getTime();
        newProduct.description = "";
        newProduct.semanticUrl = "";
        newProduct.shortDescription = "";

        for (String img : productDTO.images) {
            Image image = createImage(img, newProduct.name + "_" + StringUtils.getLastSubUrl(img), newProduct);
            newProduct.images.add(image);

        }
        newProduct.save();
    }


    private static byte[] downloadImage(String urlStr) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            Logger.warn("malformed image url:" + urlStr);
            return null;
        }
        try (InputStream in = new BufferedInputStream(url.openStream());
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            Logger.warn("error while reading image:" + url);
            return null;
        }
    }

    static String uploadAndSaveFile(String fileName, byte[] bytes) {
        String relativePath = Play.application().configuration().getString("sapsan.upload.path", "media");
        File absolutePath = Play.application().getFile(relativePath);
        relativePath = UploadUtils.uploadAndSaveFile(bytes, fileName, absolutePath);
        return relativePath;
    }

    private static Image createImage(String url, String name, Product p) {
        byte[] imageData = downloadImage(url);
        if (imageData == null) return null;
        String fileName = uploadAndSaveFile(name, imageData);
        Image image = new Image();
        image.name = name;
        image.filename = fileName;
        image.product = p;
        image.color = "";
        return image;
    }
}
