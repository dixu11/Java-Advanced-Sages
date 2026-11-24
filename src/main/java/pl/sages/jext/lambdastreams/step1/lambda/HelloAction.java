package pl.sages.jext.lambdastreams.step1.lambda;

/** Sposób III, wariant z klasą implementującą interfejs. */
public class HelloAction implements ButtonAction {
    @Override
    public void clickAction() {
        System.out.println("Hello!");
    }
}
