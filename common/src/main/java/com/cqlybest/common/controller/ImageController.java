package com.cqlybest.common.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cqlybest.common.bean.Image;
import com.cqlybest.common.service.ImageService;

@Controller
public class ImageController {

  @Autowired
  private ImageService imageService;

  @RequestMapping(value = "/{imageId}.{imageType:jpg|png|gif}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> view(@PathVariable String imageId, @PathVariable String imageType,
      @RequestParam(required = false) Integer width,
      @RequestParam(required = false) Integer height,
      @RequestHeader(value = "If-Modified-Since", required = false) String ifModifiedSince)
      throws IOException {
    if (ifModifiedSince != null) {
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    Image image = imageService.get(imageId);
    if (!imageType.equals(image.getImageType())) {
      return new ResponseEntity<byte[]>(null, null, HttpStatus.NOT_FOUND);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setDate(System.currentTimeMillis());
    headers.setLastModified(0);
    headers.setExpires(System.currentTimeMillis() + 31536000000L);
    headers.setCacheControl("max-age=31536000000");
    if (imageType.equals("jpg")) {
      headers.setContentType(MediaType.IMAGE_JPEG);
    } else if (imageType.equals("png")) {
      headers.setContentType(MediaType.IMAGE_PNG);
    } else if (imageType.equals("gif")) {
      headers.setContentType(MediaType.IMAGE_GIF);
    }

    if (width == null || height == null) {
      return new ResponseEntity<byte[]>(image.getImageData(), headers, HttpStatus.OK);
    }
    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image.getImageData()));
    int oWidth = bufferedImage.getWidth();
    int oHeight = bufferedImage.getHeight();
    BufferedImage scaleImage;
    if (oWidth / (double) oHeight > width / (double) height) {
      double rate = oHeight / (double) height;
      scaleImage = Scalr.resize(bufferedImage, (int) (oWidth / rate), height);
      int x = (scaleImage.getWidth() - width) / 2;
      scaleImage = Scalr.crop(scaleImage, x, 0, width, height);
    } else {
      double rate = oWidth / (double) width;
      scaleImage = Scalr.resize(bufferedImage, width, (int) (oHeight / rate));
      int y = (scaleImage.getHeight() - height) / 2;
      scaleImage = Scalr.crop(scaleImage, 0, y, width, height);
    }
    ByteArrayOutputStream scaleOut = new ByteArrayOutputStream();
    ImageIO.write(scaleImage, image.getImageType(), scaleOut);
    return new ResponseEntity<byte[]>(scaleOut.toByteArray(), headers, HttpStatus.OK);
  }
}