package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LocatorBuilder {
    private String locator;
    private String name;
    private boolean slashNeeded = false;
    private WebElement root;

    private LocatorBuilder(String startWith) {
        locator = startWith;
    }

    public static LocatorBuilder create() {
        return new LocatorBuilder(XpathHelper.getRoot());
    }

    public LocatorBuilder textEqual(String text) {
        return custom(XpathHelper.getTextEquals(text));
    }

    public LocatorBuilder custom(String xpathPart) {
        locator += slashIfNeeded() + xpathPart;
        slashNeeded = true;
        return this;
    }

    public LocatorBuilder textContains(String text) {
        return custom(XpathHelper.getTextContains(text));
    }

    public LocatorBuilder textStartsWith(String text) {
        return custom(XpathHelper.getTextStartsWith(text));
    }

    public LocatorBuilder axes(String axes) {
        locator += slashIfNeeded() + axes + "::";
        slashNeeded = false;
        return this;
    }

    public LocatorBuilder following() {
        return axes("following");
    }

    public LocatorBuilder followingSibling() {
        return axes("following-sibling");
    }

    public LocatorBuilder preceding() {
        return axes("preceding");
    }

    public LocatorBuilder precedingSibling() {
        return axes("preceding-sibling");
    }


    public EnhancedElement buildWeb() {
        return new EnhancedElement(getName(), "", By.xpath(locator), root);
    }

    public LocatorBuilder classEqual(String id) {
        return custom(XpathHelper.getClassEqual(id));
    }

    public LocatorBuilder classContains(String id) {
        return custom(XpathHelper.getClassContains(id));
    }

    public LocatorBuilder idEqual(String id) {
        return custom(XpathHelper.getIdEqual(id));
    }

    public LocatorBuilder idContains(String id) {
        return custom(XpathHelper.getIdContains(id));
    }

    public LocatorBuilder nameEqual(String name) {
        return custom(XpathHelper.getNameEqual(name));
    }

    public LocatorBuilder nameContains(String name) {
        return custom(XpathHelper.getNameContains(name));
    }

    public LocatorBuilder nameStartsWith(String name) {
        return custom(XpathHelper.getNameStartsWith(name));
    }

    private String slashIfNeeded() {
        return slashNeeded ? "/" : "";
    }

    private String getName() {
        return name == null ? locator : name;
    }

}
