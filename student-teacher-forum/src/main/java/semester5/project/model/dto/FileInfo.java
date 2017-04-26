package semester5.project.model.dto;

public class FileInfo {

	private String basename;
	private String extention;
	private String subDirectory;
	private String BaseDirectory;

	public FileInfo(String basename, String extention, String subDirectory, String baseDirectory) {
		this.basename = basename;
		this.extention = extention;
		this.subDirectory = subDirectory;
		this.BaseDirectory = baseDirectory;
	}

	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getSubDirectory() {
		return subDirectory;
	}

	public void setSubDirectory(String subDirectory) {
		this.subDirectory = subDirectory;
	}

	public String getBaseDirectory() {
		return BaseDirectory;
	}

	public void setBaseDirectory(String baseDirectory) {
		BaseDirectory = baseDirectory;
	}

	@Override
	public String toString() {
		return "FileInfo [basename=" + basename + ", extention=" + extention + ", subDirectory=" + subDirectory
				+ ", BaseDirectory=" + BaseDirectory + "]";
	}

}
