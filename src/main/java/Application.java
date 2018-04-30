import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import ru.kirillyarulin.gradientdescent.*;

/**
 * Created by Kirill Yarulin on 28.04.18
 */
public class Application {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Gradient descent").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        String file = "data/bigcity.csv";
//        String file = "data/test-data.csv";

        JavaRDD<LabeledPoint> data = GDUtils.loadCSVFile(sc, file);

        GradientDescent gradientDescent = new GradientDescent(200,0.000001,0.001);
//        GradientDescent gradientDescent = new GradientDescent(100,0.1,0.001);

        Vector result = gradientDescent.optimise(data, new Vector(0, 0));
        System.out.println(result);
        GDUtils.showGraph(data.collect(),result);

    }



}
