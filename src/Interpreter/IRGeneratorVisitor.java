package Interpreter;

import RepresentacionIntermedia.CodigoIntermedio;

public class IRGeneratorVisitor extends InterpreterBaseVisitor<String> {
    private int tempCount = 0;
    private int labelCount = 0;

    private CodigoIntermedio codigo = new CodigoIntermedio();

    private boolean inFunction = false;
    private String currentFunctionName = null;

    private String newTemp() {
        return "t" + (tempCount++);
    }

    private String newLabel() {
        return "L" + (labelCount++);
    }

    public CodigoIntermedio getCodigo() {
        return codigo;
    }

    @Override
    public String visitAssignment(InterpreterParser.AssignmentContext ctx) {
        String expr = visit(ctx.expression());
        String id = ctx.ID() != null ? ctx.ID().getText() : ctx.arrayAccess().getText();

        codigo.agregar("=", expr, "", id);

        if (inFunction && id.equals(currentFunctionName)) {
            codigo.agregar("return", expr, "", "");
        }

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
            codigo.agregar(op, left, right, temp);
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
            codigo.agregar(op, left, right, temp);
            left = temp;
        }
        return left;
    }

    @Override
    public String visitFactor(InterpreterParser.FactorContext ctx) {
        if (ctx.ID() != null) {
            String idText = ctx.ID().getText();
            if (idText.equalsIgnoreCase("true")) return "true";
            if (idText.equalsIgnoreCase("false")) return "false";
            return idText;
        }
        if (ctx.INT_CONST() != null) return ctx.INT_CONST().getText();

        if (ctx.expression() != null) return visit(ctx.expression());
        if (ctx.functionCall() != null) return visit(ctx.functionCall());

        if (ctx.NOT() != null) {
            String operand = visit(ctx.factor());
            String temp = newTemp();
            codigo.agregar("not", operand, "", temp);
            return temp;
        }

        return "";
    }

    @Override
    public String visitFunctionCall(InterpreterParser.FunctionCallContext ctx) {
        java.util.List<String> args = new java.util.ArrayList<>();
        if (ctx.expression().size() > 0) {
            for (InterpreterParser.ExpressionContext expr : ctx.expression()) {
                args.add(visit(expr));
            }
        }
        for (String arg : args) {
            codigo.agregar("param", arg, "", "");
        }
        String temp = newTemp();
        codigo.agregar("call", ctx.ID().getText(), Integer.toString(args.size()), temp);
        return temp;
    }

    @Override
    public String visitIfStatement(InterpreterParser.IfStatementContext ctx) {
        String condition = visit(ctx.expression());
        String labelEnd = newLabel();

        if (ctx.statement().size() == 1) {
            codigo.agregar("ifFalse", condition, "", labelEnd);
            visit(ctx.statement(0));
            codigo.agregar("label", "", "", labelEnd);
        } else {
            String labelElse = newLabel();
            codigo.agregar("ifFalse", condition, "", labelElse);
            visit(ctx.statement(0));
            codigo.agregar("goto", "", "", labelEnd);
            codigo.agregar("label", "", "", labelElse);
            visit(ctx.statement(1));
            codigo.agregar("label", "", "", labelEnd);
        }

        return null;
    }

    @Override
    public String visitComparisonExpr(InterpreterParser.ComparisonExprContext ctx) {
        String left = visit(ctx.arithmeticExpr(0));

        if (ctx.arithmeticExpr().size() > 1) {
            String right = visit(ctx.arithmeticExpr(1));
            String op = ctx.getChild(1).getText();
            String temp = newTemp();
            codigo.agregar(op, left, right, temp);
            return temp;
        }

        return left;
    }

    @Override
    public String visitWhileStatement(InterpreterParser.WhileStatementContext ctx) {
        String startLabel = newLabel();
        String endLabel = newLabel();

        codigo.agregar("label", "", "", startLabel);
        String condition = visit(ctx.expression());
        codigo.agregar("ifFalse", condition, "", endLabel);

        visit(ctx.statement());

        codigo.agregar("goto", "", "", startLabel);
        codigo.agregar("label", "", "", endLabel);

        return null;
    }

    @Override
    public String visitRepeatStatement(InterpreterParser.RepeatStatementContext ctx) {
        String startLabel = newLabel();

        codigo.agregar("label", "", "", startLabel);

        visit(ctx.statements());

        String condition = visit(ctx.expression());

        codigo.agregar("ifFalse", condition, "", startLabel);

        return null;
    }

    @Override
    public String visitFunctionDecl(InterpreterParser.FunctionDeclContext ctx) {
        inFunction = true;
        currentFunctionName = ctx.ID().getText();

        codigo.agregar("label", "", "", currentFunctionName);

        visit(ctx.block());

        codigo.agregar("end", "", "", currentFunctionName);

        currentFunctionName = null;
        inFunction = false;

        return null;
    }

    @Override
    public String visitLogicalExpr(InterpreterParser.LogicalExprContext ctx) {
        if (ctx.comparisonExpr().size() == 1) {
            return visit(ctx.comparisonExpr(0));
        }

        String left = visit(ctx.comparisonExpr(0));
        for (int i = 1; i < ctx.comparisonExpr().size(); i++) {
            String right = visit(ctx.comparisonExpr(i));
            String op = ctx.getChild(2 * i - 1).getText().toLowerCase();

            String temp = newTemp();
            codigo.agregar(op, left, right, temp);
            left = temp;
        }
        return left;
    }

    @Override
    public String visitWriteStatement(InterpreterParser.WriteStatementContext ctx) {
        // El primer hijo dentro de paréntesis es la lista de expresiones opcional
        if (ctx.expression() != null && !ctx.expression().isEmpty()) {
            for (InterpreterParser.ExpressionContext exprCtx : ctx.expression()) {
                String expr = visit(exprCtx);
                codigo.agregar("write", expr, "", "");
            }
        }
        return null;
    }
}