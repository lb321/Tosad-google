package domain.businessRuleType;

import org.antlr.stringtemplate.StringTemplate;

/**
 * Created by lucas on 19-1-2017.
 */
public class MSSQLTemplate implements ITemplate {
    private StringTemplate template;

    public MSSQLTemplate(StringTemplate template) {
        this.template = template;
    }

    @Override
    public StringTemplate getTemplate() {
        return template;
    }
}
