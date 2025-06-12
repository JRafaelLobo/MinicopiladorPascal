package Interpreter;

import java.util.ArrayList;
import java.util.List;

public class IRGeneratorVisitor extends InterpreterBaseVisitor<String> {
    private int tempCount = 0;
    private int labelCount = 0;
    private final List<String> code = new ArrayList<>();

    private String newTemp() {
        return "t" + (tempCount++);
    }

    private String newLabel() {
        return "L" + (labelCount++);
    }

    public List<String> getCode() {
        return code;
    }

    @Override
    public String visitAssignment(InterpreterParser.AssignmentContext ctx) {
        String expr = visit(ctx.expression());
        String id = ctx.ID() != null ? ctx.ID().getText() : ctx.arrayAccess().getText();
        code.add(id + " = " + expr);
        return null;
    }

    @Override
    public String visitArithmeticExpr(InterpreterParser.ArithmeticExprContext ctx) {
        if (ctx.term().size() == 1) {
            return visit(ctx.term(0));
        }

        String left = visit(ctx.term(0));
        for (int i = 1; i < ctx.term().size(); i++) {
            String right = visit(ctx.term(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String temp = newTemp();
            code.add(temp + " = " + left + " " + op + " " + right);
            left = temp;
        }
        return left;
    }

    @Override
    public String visitTerm(InterpreterParser.TermContext ctx) {
        if (ctx.factor().size() == 1) {
            return visit(ctx.factor(0));
        }

        String left = visit(ctx.factor(0));
        for (int i = 1; i < ctx.factor().size(); i++) {
            String right = visit(ctx.factor(i));
            String op = ctx.getChild(2 * i - 1).getText();
            String temp = newTemp();
            code.add(temp + " = " + left + " " + op + " " + right);
            left = temp;
        }
        return left;
    }

    @Override
    public String visitFactor(InterpreterParser.FactorContext ctx) {
        if (ctx.ID() != null) return ctx.ID().getText();
        if (ctx.INT_CONST() != null) return ctx.INT_CONST().getText();
        if (ctx.expression() != null) return visit(ctx.expression());
        if (ctx.functionCall() != null) return visit(ctx.functionCall());
        return "";
    }

    @Override
    public String visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        String functionName = ctx.ID().getText();
        String temp = newTemp(); // almacena valor retornado
        code.add(temp + " = call " + functionName);
        return temp;
    }
}
