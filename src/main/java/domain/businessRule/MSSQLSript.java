package domain.businessRule;

import domain.businessRuleType.ITemplate;
import domain.businessRuleType.MSSQLTemplate;
import domain.businessRuleType.OracleTemplate;
import org.antlr.stringtemplate.StringTemplate;

import java.util.List;

/**
 * Created by lucas on 19-1-2017.
 */
public class MSSQLSript extends Script {
    private StringTemplate triggerTemplate = new StringTemplate(
                "CREATE TRIGGER $trigger_name$ ON $table_name$ \n" +
                "$trigger_event$\n" +
                "AS\n"+
                "$trigger_code$\n"+
                "GO"
    );

    public MSSQLSript(String name, List<String> triggerEvent, BusinessRule businessRule) {
        super(name, triggerEvent, businessRule);
    }

    @Override
    public void generate() {
        StringTemplate constraintTemplate = null;
        for(ITemplate t : businessRule.getBusinessRuleType().getTemplates()){
            if(t instanceof MSSQLTemplate){
                constraintTemplate = t.getTemplate();
                break;
            }
        }
        triggerTemplate.setAttribute("trigger_name", name);
        String event = "";
        for(String s : triggerEvent){
            event += "FOR " +  s + " OR ";
        }
        event = event.substring(0, event.length() - 3);
        triggerTemplate.setAttribute("trigger_event", event);
        triggerTemplate.setAttribute("table_name", "table_name");
        triggerTemplate.setAttribute("error_message", businessRule.getErrorMessage());
        for(Definition definition : businessRule.getDefinitions()){
            constraintTemplate.setAttribute(definition.getName(), definition.getValue());
        }
        constraintTemplate.setAttribute("operator", businessRule.getOperator().getOperator());
        constraintTemplate.setAttribute("firstAttribute", businessRule.getFirstAttribute().getName());
        triggerTemplate.setAttribute("trigger_code", "l_passed := " + constraintTemplate.toString());
        System.out.println(triggerTemplate);
    }
}
