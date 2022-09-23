import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {
    private String fileNameToSearch;
    private List<String> result = new ArrayList<String>();
    private String error = "";
    private String extension = "";

    public String getFileNameToSearch() {
        return fileNameToSearch;
    }

    public void setFileNameToSearch(String fileNameToSearch) {
        this.fileNameToSearch = fileNameToSearch;
    }

    public List<String> getResult() {
        return result;
    }

    public void searchDirectory(File directory, String fileNameToSearch) {
        setFileNameToSearch(fileNameToSearch);
        if (directory.isDirectory()) {
            search(directory);
        }
    }
    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public void setExtesion(String extension) {
        this.extension = extension;
    }

    public String getExtesion() {
        return this.extension;
    }

    private void search(File file) {
        if (file.isDirectory()) {
            if (file.canRead()) {
                for (File temp : file.listFiles()) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        if (temp.getName().split("\\.").length > 1) {
                            setExtesion(".".concat(temp.getName().split("\\.")[1]));
                        }
                        if (!getExtesion().isEmpty()) {
                            if (getFileNameToSearch().concat(getExtesion()).equalsIgnoreCase(temp.getName().toLowerCase())) {
                                result.add(temp.getAbsoluteFile().toString());
                            }
                        }

                    }
                }

            } else {
                setError("Sem permissão para leitura no diretório");
            }
        }

    }
}
