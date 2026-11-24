package pl.sages.jext.examples.ch2.annotations;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationParameterValue;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.List;

/**
 * Slajd 60: wyszukanie klas z adnotacją {@code @Route} przez ClassGraph.
 * <p>
 * Poprawki względem slajdu (dziennik błędów): ClassGraph nie ma {@code enableClasspath()} ani
 * {@code getAllAnnotationInfo(String)}; pojedynczą adnotację zwraca {@code getAnnotationInfo(String)}.
 * Pakiet {@code com.xyz} zamieniony na pakiet przykładu.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.ClassGraphRoute}
 */
public class ClassGraphRoute {

    public static void main(String[] args) {
        String pkg = "pl.sages.jext.examples.ch2.annotations.route";
        String routeAnnotation = pkg + ".Route";
        try (ScanResult scanResult = new ClassGraph()
                .enableClassInfo().enableAnnotationInfo()
                .acceptPackages(pkg).scan()) {
            for (ClassInfo routeClassInfo : scanResult.getClassesWithAnnotation(routeAnnotation)) {
                AnnotationInfo routeAnnotationInfo = routeClassInfo.getAnnotationInfo(routeAnnotation);
                List<AnnotationParameterValue> routeParamVals = routeAnnotationInfo.getParameterValues();
                String route = (String) routeParamVals.getFirst().getValue();
                System.out.println(routeClassInfo.getName() + " is annotated with route " + route);
            }
        }
    }
}
