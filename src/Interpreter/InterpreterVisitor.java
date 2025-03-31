// Generated from C:/Users/jflg2/Desktop/Java/Interpreter 1/src/Interpreter.g4 by ANTLR 4.13.2
package Interpreter;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link InterpreterParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface InterpreterVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(InterpreterParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(InterpreterParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarations(InterpreterParser.DeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#varDeclList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclList(InterpreterParser.VarDeclListContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(InterpreterParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(InterpreterParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#indexList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexList(InterpreterParser.IndexListContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#statements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatements(InterpreterParser.StatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(InterpreterParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(InterpreterParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(InterpreterParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(InterpreterParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(InterpreterParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#repeatStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatStatement(InterpreterParser.RepeatStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#readStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadStatement(InterpreterParser.ReadStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#writeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStatement(InterpreterParser.WriteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(InterpreterParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(InterpreterParser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(InterpreterParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#logicalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpr(InterpreterParser.LogicalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#comparisonExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpr(InterpreterParser.ComparisonExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#arithmeticExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmeticExpr(InterpreterParser.ArithmeticExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(InterpreterParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(InterpreterParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#functionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDecl(InterpreterParser.FunctionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#procedureDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProcedureDecl(InterpreterParser.ProcedureDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(InterpreterParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(InterpreterParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link InterpreterParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(InterpreterParser.ParamContext ctx);
}