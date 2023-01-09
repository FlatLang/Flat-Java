package flat.java.nodewriters;

import flat.tree.Closure;
import flat.tree.Identifier;

public abstract class IdentifierWriter extends ValueWriter implements AccessibleWriter {
    public abstract Identifier node();

    @Override
    public StringBuilder writeExpression(StringBuilder builder) {
        if (node() != node().getReturnedNode() && node().getReturnedNode() instanceof Closure && !node().isAccessed()) {
            getWriter((Closure)node().getReturnedNode()).writeLambdaParams(builder);
        }

        writeUseExpression(builder);
        writeAccessedExpression(builder);

        return builder;
    }

    public StringBuilder writeUseExpression(StringBuilder builder) {
        return writeName(builder).append(writeArrayAccess());
    }

    public StringBuilder writeName() {
        return writeName(new StringBuilder());
    }

    public StringBuilder writeName(StringBuilder builder) {
        switch (node().getName()) {
            case "arguments": return builder.append("_java_arguments");
            case "public": return builder.append("_java_public");
            case "private": return builder.append("_java_private");
            case "package": return builder.append("_java_package");
            case "class": return builder.append("_java_class");
            case "default": return builder.append("_java_default");
            case "case": return builder.append("_java_case");
            case "function": return builder.append("_java_function");
            case "char": return builder.append("_java_char");
            default: return builder.append(node().getName());
        }
    }
}