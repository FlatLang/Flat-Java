package flat.java.nodewriters;

import flat.tree.Closure;
import flat.tree.ExtensionMethodDeclaration;
import flat.tree.Identifier;
import flat.tree.ReferenceParameter;
import flat.tree.variables.ObjectReference;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter {
    public abstract Identifier node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder) {
        if (node() != node().getReturnedNode() && node().getReturnedNode() instanceof Closure && !node().isAccessed()) {
            getWriter((Closure)node().getReturnedNode()).writeLambdaParams(builder);
        }

        if (node().doesAccess() && node().getAccessedNode().isChainNavigation()) {
            builder.append("FlatUtilities.chain(");
            writeUseExpression(builder).append(", _cr -> _cr.");
            return writeAccessedExpression(builder, false).append(")");
        }

        writeUseExpression(builder);
        writeAccessedExpression(builder);

        return builder;
    }

    public StringBuilder writeExtensionReferenceAccess(StringBuilder builder) {
        if (node().getParentMethod() instanceof ExtensionMethodDeclaration == false) return builder;
        if (node().getReferenceTypeNode() instanceof ObjectReference == false) return builder;
        if (node().isAccessedWithinStaticContext()) return builder;

        return builder.append("_this.");
    }

    public StringBuilder writeUseExpression(StringBuilder builder) {
        writeExtensionReferenceAccess(builder);
        return writeName(builder).append(writeArrayAccess());
    }

    public final StringBuilder writeName() {
        return writeName(new StringBuilder());
    }

    public final StringBuilder writeName(StringBuilder builder) {
        return writeName(builder, null);
    }

    public StringBuilder writeName(StringBuilder builder, String name) {
        name = name != null ? name : node().getName();

        switch (name) {
            case "arguments": return builder.append("_java_arguments");
            case "public": return builder.append("_java_public");
            case "private": return builder.append("_java_private");
            case "package": return builder.append("_java_package");
            case "class": return builder.append("_java_class");
            case "default": return builder.append("_java_default");
            case "case": return builder.append("_java_case");
            case "function": return builder.append("_java_function");
            case "char": return builder.append("_java_char");
            default: return builder.append(name);
        }
    }

    public final StringBuilder writeOptionalName()
    {
        return writeOptionalName(new StringBuilder());
    }

    public final StringBuilder writeOptionalName(StringBuilder builder)
    {
        return writeOptionalName(builder, null);
    }

    public StringBuilder writeOptionalName(StringBuilder builder, String name)
    {
        return writeName(builder, name).append("_optional");
    }

}