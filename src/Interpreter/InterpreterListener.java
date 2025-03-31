// Generated from C:/Users/jflg2/Desktop/Java/Interpreter 1/src/Interpreter.g4 by ANTLR 4.13.2
package Interpreter;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link InterpreterParser}.
 */
public interface InterpreterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(InterpreterParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(InterpreterParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(InterpreterParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(InterpreterParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#declarations}.
	 * @param ctx the parse tree
	 */
	void enterDeclarations(InterpreterParser.DeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#declarations}.
	 * @param ctx the parse tree
	 */
	void exitDeclarations(InterpreterParser.DeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#varDeclList}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclList(InterpreterParser.VarDeclListContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#varDeclList}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclList(InterpreterParser.VarDeclListContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(InterpreterParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(InterpreterParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(InterpreterParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(InterpreterParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#indexList}.
	 * @param ctx the parse tree
	 */
	void enterIndexList(InterpreterParser.IndexListContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#indexList}.
	 * @param ctx the parse tree
	 */
	void exitIndexList(InterpreterParser.IndexListContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(InterpreterParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(InterpreterParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(InterpreterParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(InterpreterParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(InterpreterParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(InterpreterParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(InterpreterParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(InterpreterParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(InterpreterParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(InterpreterParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(InterpreterParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(InterpreterParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#repeatStatement}.
	 * @param ctx the parse tree
	 */
	void enterRepeatStatement(InterpreterParser.RepeatStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#repeatStatement}.
	 * @param ctx the parse tree
	 */
	void exitRepeatStatement(InterpreterParser.RepeatStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void enterReadStatement(InterpreterParser.ReadStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#readStatement}.
	 * @param ctx the parse tree
	 */
	void exitReadStatement(InterpreterParser.ReadStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#writeStatement}.
	 * @param ctx the parse tree
	 */
	void enterWriteStatement(InterpreterParser.WriteStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#writeStatement}.
	 * @param ctx the parse tree
	 */
	void exitWriteStatement(InterpreterParser.WriteStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(InterpreterParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(InterpreterParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccess(InterpreterParser.ArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#arrayAccess}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccess(InterpreterParser.ArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(InterpreterParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(InterpreterParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#logicalExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpr(InterpreterParser.LogicalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#logicalExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpr(InterpreterParser.LogicalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(InterpreterParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(InterpreterParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 */
	void enterArithmeticExpr(InterpreterParser.ArithmeticExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 */
	void exitArithmeticExpr(InterpreterParser.ArithmeticExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(InterpreterParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(InterpreterParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(InterpreterParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(InterpreterParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDecl(InterpreterParser.FunctionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#functionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDecl(InterpreterParser.FunctionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#procedureDecl}.
	 * @param ctx the parse tree
	 */
	void enterProcedureDecl(InterpreterParser.ProcedureDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#procedureDecl}.
	 * @param ctx the parse tree
	 */
	void exitProcedureDecl(InterpreterParser.ProcedureDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(InterpreterParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(InterpreterParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(InterpreterParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(InterpreterParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link InterpreterParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(InterpreterParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link InterpreterParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(InterpreterParser.ParamContext ctx);
}