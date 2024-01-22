package mezo.restmvc.spring_6_rest_mvc.mezoutils;

import lombok.*;
import lombok.experimental.SuperBuilder;
import mezo.restmvc.spring_6_rest_mvc.entities.OrderLine;
import org.flywaydb.core.internal.util.JsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder(builderClassName = "MyBuilder")
class zA {

    private int age;

    @Singular
    private List<Integer> nbs;


    public static class MyBuilder {
        private int age = 30; // Default value

        public MyBuilder age(int age) {
            System.out.println(age);
            this.age = age;
            return this;
        }

        public zA build() {
            List nbs;
            switch (this.nbs == null ? 0 : this.nbs.size()) {
                case 0:
                    nbs = Collections.emptyList();
                    break;
                case 1:
                    nbs = Collections.singletonList((Integer) this.nbs.get(0));
                    break;
                default:
                    nbs = Collections.unmodifiableList(new ArrayList(this.nbs));
            }


            System.out.println("asdfsdfkjsd;kflj");
            return new zA(this.age, nbs);
        }
    }
}


@SuperBuilder
class A {
    int aa;


    void afterBuild() {
        System.out.println("from parent");
    }

    ;
}

@SuperBuilder
class B extends A {

    int bb;

    private static final class BBuilderImpl extends BBuilder<B, BBuilderImpl> {

        public B build() {

            B b = new B(this);

            return b;
        }
    }

}

final class UtilTest {

    public static void main(String[] args) throws IllegalAccessException {


        var sorderLines = Collections.emptyList();
        var orderLines2 = Collections.singletonList(2);
        var orderLines3 = Collections.unmodifiableList(new ArrayList(3));

//        sorderLines.add(1);
//        orderLines2.add(1);
        orderLines3.add(1);


    }


}


