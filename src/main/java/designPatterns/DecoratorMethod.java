package designPatterns;

interface DataSource{
    void writeData(String data);
    String readData();
}

class FileDataSource implements DataSource{

    private String filename;

    public FileDataSource(String filename){
        this.filename = filename;
    }

    @Override
    public void writeData(String data) {
        // write to a file
        System.out.println(data);
    }

    @Override
    public String readData() {
        // read from a file
        return "compressed:encrypted:data from source";
    }
}

class EncryptionDecorator implements DataSource{
    private DataSource wrapped;

    public EncryptionDecorator(DataSource source){
        this.wrapped = source;
    }

    @Override
    public void writeData(String data) {
        String encrypted = encrypt(data);
        wrapped.writeData(encrypted);
    }

    @Override
    public String readData() {
        String data = wrapped.readData();
        return decrypt(data);
    }

    private String encrypt(String data){
        return "encrypted:" + data;
    }

    private String decrypt(String data){
        return data.replace("encrypted:", "");
    }

}

class CompressionDecorator implements DataSource{
    private DataSource wrapped;

    public CompressionDecorator(DataSource source){
        this.wrapped = source;
    }

    @Override
    public void writeData(String data) {
        String compressed = compress(data);
        wrapped.writeData(compressed); // Delegate to wrapped object
    }

    @Override
    public String readData() {
        String data = wrapped.readData();
        return decompress(data);
    }

    private String compress(String data){
        return "compressed:" + data;
    }

    private String decompress(String data){
        return data.replace("compressed:", "");
    }
}

public class DecoratorMethod {
    public static void main(String[] args) {
        DataSource source = new FileDataSource("data.txt");
        source = new EncryptionDecorator(source);
        source = new CompressionDecorator(source);
//        source.writeData("sensitive info");
//        Data gets compressed, then encrypted, then written to file

        String output = source.readData();
        // Data gets read, then decrypted, and then decompressed
        System.out.println(output);
    }
}
