package com.cout970.gl.shader.loader;

/**
 * Created by cout970 on 05/05/2016.
 */
public class RequirementPlaceHolder implements IRequirement {

    private String name;
    private IRequirement code;

    public RequirementPlaceHolder(String dep) {
        name = dep;
    }

    public void setCode(IRequirement code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String nextLine() {
        return code.nextLine();
    }

    @Override
    public boolean hasNextLine() {
        return code != null && code.hasNextLine();
    }

    @Override
    public void reset() {
        if (code != null) {
            code.reset();
        }
    }
}
