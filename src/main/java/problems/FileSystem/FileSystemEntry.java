package problems.FileSystem;

public abstract class FileSystemEntry {
// - name : String
// - parent : Folder // here we will store parent ref, instead of string
// + getPath: String
// + isDirectory() // abstract

    String name;
    Folder parent;

    public FileSystemEntry(String name) {
        this.name = name;
        this.parent = null;
    }

    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public String getName(){
        return name;
    }

    public String getPath(){
        if(parent == null){
            return null;
        }

        String parentPath = parent.getPath();
        if(parentPath == "/"){
            return "/" + name;
        }else{
            return parentPath + "/" + name;
        }
    }

    public abstract boolean isDirectory();
}
