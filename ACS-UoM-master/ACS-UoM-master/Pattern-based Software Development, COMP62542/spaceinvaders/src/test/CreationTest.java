package test;

import creation.IFactory;
import creation.MakeFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Martin on 22/04/2016.
 */
public class CreationTest {

    @Test
    public void FactoryTest() {
        // TODO Auto-generated method stub
        IFactory f1 = MakeFactory.getFactory(); //use singleton
        IFactory f2 = MakeFactory.getFactory(); //use singleton testing.'
//        IFactory f3 = null;
        Assert.assertEquals("Two instances should be the same.",f1, f2);
//        Assert.assertEquals("Two instances should be the same.",f1, f3);
    }
}
