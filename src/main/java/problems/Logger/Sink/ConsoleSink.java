package problems.Logger.Sink;

public class ConsoleSink implements Sink{
    @Override
    public void write(String formatted) {
        System.out.println(formatted);
    }
}
