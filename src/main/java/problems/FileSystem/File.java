package problems.FileSystem;

public class File extends FileSystemEntry{
// - content : String
// + isDirectory() : false

    String content;
    public File(String name, String content){
        super(name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
