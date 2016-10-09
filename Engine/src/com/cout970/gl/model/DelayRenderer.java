package com.cout970.gl.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by cout970 on 09/05/2016.
 */
public abstract class DelayRenderer extends AbstractRenderer {

    protected Map<BatchData, List<IRenderableObject>> buffer;

    public DelayRenderer() {
        buffer = new HashMap<>();
    }

    @Override
    public void render() {
        for (Map.Entry<BatchData, List<IRenderableObject>> entry : buffer.entrySet()){
            IRenderableObject first = entry.getValue().get(0);
            bindTexture(first.getMaterial().getTextures());
            bindMaterial(first.getMaterial());
            for(IRenderableObject obj : entry.getValue()){
                processObject(obj);
                bindVAO(obj.getVAO());
                bindVBOs(obj.getVAO().getVertexAttribCount());
                draw(obj.getVAO().getDrawMode(), obj.getVAO().getVertexCount(), obj.getVAO().useElements());
                unbindVBOs(obj.getVAO().getVertexAttribCount());
            }
            unbindVAO();
            unbindTextures(first.getMaterial().getTextures().length);
        }
        buffer.clear();
    }

    protected abstract void bindMaterial(IMaterial material);

    protected abstract void processObject(IRenderableObject obj);

    public void addObjectToRender(IRenderableObject obj){
        BatchData data = new BatchData(obj.getVAO().getID(), obj.getMaterial().getID());
        put(data, obj);
    }

    protected void put(BatchData data, IRenderableObject obj){
        List<IRenderableObject> list = buffer.get(data);
        if (list == null){
            list = new LinkedList<>();
            buffer.put(data, list);
        }
        list.add(obj);
    }

    protected static class BatchData {

        private int vao;
        private int texture;

        public BatchData(int vao, int texture) {
            this.vao = vao;
            this.texture = texture;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (!(o instanceof BatchData)) { return false; }

            BatchData batchData = (BatchData) o;
            return vao == batchData.vao && texture == batchData.texture;
        }

        @Override
        public int hashCode() {
            int result = vao;
            result = 31 * result + texture;
            return result;
        }
    }
}
