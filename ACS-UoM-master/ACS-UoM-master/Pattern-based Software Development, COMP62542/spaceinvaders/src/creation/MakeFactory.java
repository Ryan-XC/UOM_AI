package creation;

/**
 * Created by Martin on 22/04/2016.
 */
public class MakeFactory {
    /**
     * Singleton Pattern
     */
    public IFactory factory;
    private static MakeFactory _factory;

    private MakeFactory(){
        factory = new Factory();
    }

    public static IFactory getFactory(){

        if(_factory == null){
            _factory = new MakeFactory();
        }
        return _factory.factory;
    }
}
