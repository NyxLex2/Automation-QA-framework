package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

class ExpectedConditionsHelper {

    protected static ExpectedCondition<WebElement> presenceOfElementLocated(WebElement root, By byLocator) {
        if (root == null) {
            return ExpectedConditions.presenceOfElementLocated(byLocator);
        } else {
            return ExpectedConditions.presenceOfNestedElementLocatedBy(root, byLocator);
        }
    }

    protected static ExpectedCondition<WebElement> visibilityOfElementLocated(By byLocator) {
        return ExpectedConditions.visibilityOfElementLocated(byLocator);
    }

    protected static ExpectedCondition<WebElement> visibilityOf(WebElement webElement) {
        return ExpectedConditions.visibilityOf(webElement);
    }

    protected static ExpectedCondition<Boolean> invisibilityOfElementLocated(By byLocator) {
        return ExpectedConditions.invisibilityOfElementLocated(byLocator);
    }

}
