package mezo.restmvc.spring_6_rest_mvc.mezoutils;

import java.util.Collection;


public class Random {

    private static java.util.Random randomNb=new java.util.Random();

    private Random(){};

    public static <T> T getRandomValueOf(Class<T> anEnum) {
        T[] values = anEnum.getEnumConstants();
        int randomIndex = new java.util.Random().nextInt(values.length);
        return values[randomIndex];
    }

    public static <T> T getRandomValueOf(Collection<T> collection){
        int currRandNb=randomNb.nextInt(collection.size());
        return collection.stream().skip(currRandNb).findFirst().get();

    }
}



