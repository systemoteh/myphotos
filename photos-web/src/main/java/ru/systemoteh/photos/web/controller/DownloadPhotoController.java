package ru.systemoteh.photos.web.controller;

import org.apache.commons.io.IOUtils;
import ru.systemoteh.photos.model.OriginalImage;
import ru.systemoteh.photos.service.PhotoService;
import ru.systemoteh.photos.web.util.UrlExtractorUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/download/*", loadOnStartup = 1)
public class DownloadPhotoController extends HttpServlet {

    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long photoId = Long.parseLong(UrlExtractorUtils.getPathVariableValue(req.getRequestURI(), "/download/", ".jpg"));
        OriginalImage originalImage = photoService.downloadOriginalImage(photoId);

        resp.setHeader("Content-Disposition", "attachment; filename="+originalImage.getName());
        resp.setContentType(getServletContext().getMimeType(originalImage.getName()));
        resp.setContentLengthLong(originalImage.getSize());
        try(InputStream in = originalImage.getIn();
            OutputStream out = resp.getOutputStream()) {
            IOUtils.copyLarge(in, out);
        }
    }
}
