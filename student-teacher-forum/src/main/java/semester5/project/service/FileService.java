package semester5.project.service;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import semester5.project.exception.ImageTooSmallException;
import semester5.project.exception.InvalidFileException;
import semester5.project.model.dto.FileInfo;

@Service
public class FileService {

	@Value("${photo.file.extention}")
	private String imageExtentions;

	private Random random = new Random();

	private String getFileExtentions(String filename) {
		int dotPosition = filename.lastIndexOf(".");

		if (dotPosition < 0) {
			return null;
		}
		return filename.substring(dotPosition + 1).toLowerCase();
	}

	private boolean isImageExtention(String extention) {
		String testExtention = extention.toLowerCase();
		for (String extentions : imageExtentions.split(",")) {
			if (testExtention.equals(extentions))
				return true;
		}
		return false;
	}

	private File makeSubdirectory(String basePath, String prefix) {
		int nDirectory = random.nextInt(1000);
		String sDirectory = String.format("%s%03d", prefix, nDirectory);

		File directory = new File(basePath, sDirectory);
		if (!directory.exists())
			directory.mkdir();
		return directory;
	}

	public FileInfo saveImageFile(MultipartFile file, String baseDirectory, String subDirPrefix, String filePrefix,
			int width, int height) throws InvalidFileException, IOException, ImageTooSmallException {
		int nFilename = random.nextInt(1000);
		String filename = String.format("%s%03d", filePrefix, nFilename);
		String extention = getFileExtentions(file.getOriginalFilename());

		if (extention == null)
			throw new InvalidFileException("No File Extention");

		if (isImageExtention(extention) == false)
			throw new InvalidFileException("Not An image File.");

		File subDirectory = makeSubdirectory(baseDirectory, subDirPrefix);

		Path filepath = Paths.get(subDirectory.getCanonicalPath(), filename + "." + extention);

		BufferedImage resizeImage = resizeImage(file, width, height);

		ImageIO.write(resizeImage, extention, filepath.toFile());

		return new FileInfo(filename, extention, subDirectory.getName(), baseDirectory);
	}

	private BufferedImage resizeImage(MultipartFile inputFile, int width, int height)
			throws IOException, ImageTooSmallException {
		BufferedImage image = ImageIO.read(inputFile.getInputStream());

		if (image.getWidth() < width || image.getHeight() < height) {
			throw new ImageTooSmallException();
		}

		double widthScale = (double) width / image.getWidth();
		double heightScale = (double) height / image.getHeight();

		double scale = Math.max(widthScale, heightScale);

		BufferedImage scaledImage = new BufferedImage((int) (scale * image.getWidth()),
				(int) (scale * image.getHeight()), image.getType());

		Graphics2D g = scaledImage.createGraphics();

		AffineTransform transform = AffineTransform.getScaleInstance(scale, scale);

		g.drawImage(image, transform, null);
		return scaledImage.getSubimage(0, 0, width, height);
	}

}
