package problems.FileSystem;

import problems.FileSystem.Exceptions.AlreadyExistException;
import problems.FileSystem.Exceptions.InvalidEntry;
import problems.FileSystem.Exceptions.NotADirectoryException;
import problems.FileSystem.Exceptions.NotFoundException;
import problems.FileSystem.Exceptions.FSInvalidPathException;

import java.util.List;

public class FileSystem {
// FileSystem:
// - root : Folder
// + createFile(path, content) : boolean
// + createFolder(path) : boolean
// + delete(path) : boolean
// + rename(path, newName) : boolean
// + move(oldPath, newPath) : boolean
// + resolvePath(path)

    Folder root;
    public FileSystem(){
        this.root = new Folder("/");
    }

    public File createFile(String path, String content) throws Exception {
        // core logic
        // 1. Parse the path to extract parent path and file name
        // 2. Resolve the parent folder (travel from root to parent path)
        // 3. Create a file and add it as child

        if(path == "/"){
            throw new Exception("Cannot create path at root address");
        }

        Folder parent = resolveParent(path);
        String fileName = extractName(path);

        if(parent.hasChild(fileName)){
            throw new AlreadyExistException("Entry file name already exist" + fileName);
        }

        File file = new File(fileName, content);
        parent.addChild(file);
        return file;
    }

    public Folder createFolder(String path) throws Exception {
        if(path == "/"){
            throw new Exception("Root already exists");
        }

        Folder parent = resolveParent(path);
        String folderName = extractName(path);

        if(parent.hasChild(folderName)){
            throw new AlreadyExistException("Entry folder name already exist" + folderName);
        }

        Folder folder = new Folder(folderName);
        parent.addChild(folder);
        return folder;
    }

    // get(path) returns the entry at the exact path
    // So get("/home/user/notes.txt") returns the File object for notes.txt,
    // while get("/home/user") returns the Folder object for user.
    public FileSystemEntry get(String path) throws NotADirectoryException, NotFoundException, FSInvalidPathException {
        return resolvePath(path);
    }

    public List<String> list(String path) throws NotADirectoryException, NotFoundException, FSInvalidPathException {
        FileSystemEntry entry = resolvePath(path);

        if(!entry.isDirectory()){
            throw new NotADirectoryException("Cannot list a file");
        }

        return ((Folder) entry).getChildren();
    }

    public void delete(String path) throws Exception {
        if (path == "/"){
            throw new Exception("Cannot delete root");
        }

        Folder parent = resolveParent(path);
        String name = extractName(path);

        FileSystemEntry removed = parent.removeChild(name);
        if(removed == null){
            throw new NotFoundException("Entry not found: " + path);
        }
    }

    public void rename(String path, String newName) throws NotADirectoryException, NotFoundException, InvalidEntry, FSInvalidPathException {
        if(path == "/"){
            throw new FSInvalidPathException("cannot rename root");
        }

        if(newName == "" || newName == null || newName.contains("/")){
            throw new FSInvalidPathException("invalid name -> cannot be empty or contains /");
        }

        Folder parent = resolveParent(path);
        String oldName = extractName(path);

        if(!parent.hasChild(oldName)){
            throw new InvalidEntry("Original file does not exist");
        }

        if(parent.hasChild(newName)){
            throw new InvalidEntry("New name file already exist");
        }

        // sequence is imp
        FileSystemEntry entry = parent.removeChild(oldName); // 1
        entry.setName(newName); // 2
        parent.addChild(entry); // 3
    }

    public void move(String srcPath, String destPath) throws NotADirectoryException, NotFoundException, FSInvalidPathException, AlreadyExistException {
//        Core logic:
//        - Find the source entry
//        - Find the destination parent folder
//        - Remove from source parent
//        - Add to destination parent
//        Edge cases:
//        - Can't move root
//        - Destination parent doesn't exist
//        - Name collision at destination
//        - Moving a folder into itself or its descendant (creates a cycle)

        if(srcPath == "/"){
            throw new FSInvalidPathException("Cannot move root");
        }

        if(srcPath == "" || srcPath == null || destPath == "" || destPath == null){
            throw new FSInvalidPathException("Cannot be null");
        }

        // get source and it's parent
        Folder srcParent = resolveParent(srcPath);
        String srcName = extractName(srcPath);
        FileSystemEntry entry = srcParent.getChild(srcName);

        if(entry == null){
            throw new NotFoundException("Source not found: " + srcPath);
        }

        // get destination parent
        Folder destParent = resolveParent(destPath);
        String destName = extractName(destPath);

        // check for cycle does not exist
//        assert entry != null;
        if(entry.isDirectory()){
            FileSystemEntry destAnct = destParent;
            while(destAnct != null){
                if(destAnct == entry){
                    throw new FSInvalidPathException("Cannot move folder into itself");
                }
                destAnct = destAnct.getParent();
            }
        }

        // check for collision at destination
        if(destParent.hasChild(destName)){
            throw new AlreadyExistException("Destination already exists: " + destPath);
        }

        // now move
        srcParent.removeChild(srcName);
        entry.setName(destName);
        destParent.addChild(entry);
    }

    private FileSystemEntry resolvePath(String path) throws NotADirectoryException, NotFoundException, FSInvalidPathException {
        if(path == "" || path == null){
            throw new FSInvalidPathException("Path cannot be null");
        }

        if(!path.startsWith("/")){
            throw new FSInvalidPathException("Path must be absolute");
        }

        // Root is special case
        if(path == "/"){
            return root;
        }

        // Split "/home/user/docs" into ["home", "user", "docs"]
        String[] parts = path.substring(1).split("/");

        FileSystemEntry current = root;
        for(String part : parts){
            if(part.isEmpty()){
                throw new FSInvalidPathException("Invalid path: consecutive slashes");
            }

            if(!current.isDirectory()){
                throw new NotADirectoryException("Not a directory");
            }

            FileSystemEntry child = ((Folder) current).getChild(part);
            if(child == null){
                throw new NotFoundException("Path not found: " + path);
            }
            current = child;
        }
        return current;
    }

    private Folder resolveParent(String path) throws NotADirectoryException, FSInvalidPathException, NotFoundException {
        // path - /home/mudit/docs/file.txt
        if(path == "/"){
            throw new FSInvalidPathException("Root has no parent");
        }

        int lastSlashIndex = path.lastIndexOf("/");

        // parentPath - /home/mudit/docs
        String parentPath = lastSlashIndex == 0 ? "/" : path.substring(0, lastSlashIndex);

        FileSystemEntry parent = resolvePath(parentPath);

        if(!parent.isDirectory()){
            throw new NotADirectoryException("Parent is not Directory");
        }

        return (Folder) parent;
    }

    private String extractName(String path){
        int lastSlashIndex = path.lastIndexOf("/");
        return path.substring(lastSlashIndex+1);
    }


}
