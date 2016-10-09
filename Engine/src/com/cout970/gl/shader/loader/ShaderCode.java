package com.cout970.gl.shader.loader;

import java.util.*;

/**
 * Created by cout970 on 05/05/2016.
 */
public class ShaderCode implements IRequirement {

    private List<ICodeLine> codeLines;
    private Map<String, IRequirement> requirements;
    private int line;
    private String[] total;
    private String name;

    public ShaderCode(String name) {
        this.name = name;
        codeLines = new LinkedList<>();
        requirements = new HashMap<>();
    }

    public void appendLine(String line){
        codeLines.add(new StringCodeLine(line));
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, IRequirement> entry : requirements.entrySet()){
            if(entry.getValue() == null){
                throw new IllegalStateException("Missing requirements: "+entry.getKey());
            }
        }

        for (ICodeLine line : codeLines) {
            while (line.hasNextLine()) {
                s.append(line.nextLine());
            }
            line.reset();
        }
        return s.toString();
    }

    public void addRequirement(String dep) {
        requirements.put(dep, null);
        codeLines.add(new RequirementPlaceHolder(dep));
    }

    public void fillRequirement(IRequirement req){
        if (!requirements.containsKey(req.getName()))
            throw new IllegalArgumentException("Invalid dependence: "+req.getName());
        requirements.put(req.getName(), req);
        for (ICodeLine c : codeLines){
            if (c instanceof RequirementPlaceHolder){
                if (((RequirementPlaceHolder) c).getName().equals(req.getName())) {
                    ((RequirementPlaceHolder) c).setCode(req);
                }
            }
        }
    }

    public List<String> getRequirements(){
        return new ArrayList<>(requirements.keySet());
    }

    @Override
    public String nextLine() {
        return getTotal()[line++];
    }

    @Override
    public boolean hasNextLine() {
        return line > getTotal().length;
    }

    private String[] getTotal() {
        if (total == null){
            total = toString().split("\n");
        }
        return total;
    }

    @Override
    public void reset() {
        line = 0;
    }

    @Override
    public String getName() {
        return name;
    }
}
