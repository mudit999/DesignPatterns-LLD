package problems.FileSystem;

import java.util.*;

public class Folder extends FileSystemEntry {
// - children : Map<String, FileSystemEntry>
// + isDirectory() : true
// + addChild : boolean
// + removeChild : FileSystemEntry
// + hasChild : boolean
// + getChildren() : List<FileSystemEntry>

    Map<String, FileSystemEntry> children;

    public Folder(String name){
        super(name);
        children = new HashMap<>();
    }

    public boolean addChild(FileSystemEntry entry){
        if(entry == null){
            return false;
        }

        if(children.containsKey(entry.getName())){
            return false; // Name collision
        }

        children.put(entry.getName(), entry);
        entry.setParent(this);// Maintain bidirectional link
        return true;
    }

    public FileSystemEntry removeChild(String name){
        FileSystemEntry child = children.remove(name);
        if(child != null){
            child.setName(null);
        }
        return child;
    }

    public FileSystemEntry getChild(String name) {
        return children.get(name);
    }

    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    public List<String> getChildren(){
        return new ArrayList<>(children.keySet());
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
