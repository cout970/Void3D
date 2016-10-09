package com.cout970.gl.model.obj;


import com.cout970.gl.model.ModelVAO;
import com.cout970.gl.resource.IResource;
import com.cout970.gl.tesellator.Tessellator;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cout970 on 29/02/2016.
 */
public class OBJLoader {

    final static String seperator = "/";
    final static String sVertex = "v ";
    final static String sNormal = "vn ";
    final static String sTexture = "vt ";
    final static String sFace = "f ";
    final static int startIndex = 1; //index after label

    public static ModelVAO getObjModel(IResource res) throws IOException{
        ObjData data = getObjData(res);
        Tessellator tes = new Tessellator();
        data.tessellate(tes);
        return tes.getData();
    }

    public static ObjData getObjData(IResource res) throws IOException {

        BufferedReader file = new BufferedReader(new InputStreamReader(res.getInputStream()));
        ObjData model = new ObjData();
        String line;
        boolean hasNormals = false;
        boolean hasTexture = false;

        while ((line = file.readLine()) != null) {
            if (line.startsWith(sVertex)) { //vertex

                model.vertices.add(new Vector3f(Float.valueOf(line.split(" ")[startIndex]),
                        Float.valueOf(line.split(" ")[startIndex + 1]),
                        Float.valueOf(line.split(" ")[startIndex + 2])));

            } else if (line.startsWith(sNormal)) { //normals

                hasNormals = true;
                model.normals.add(new Vector3f(Float.valueOf(line.split(" ")[startIndex]),
                        Float.valueOf(line.split(" ")[startIndex + 1]),
                        Float.valueOf(line.split(" ")[startIndex + 2])));

            } else if (line.startsWith(sTexture)) {

                hasTexture = true;
                model.texcoords.add(new Vector2f(Float.valueOf(line.split(" ")[startIndex]),
                        Float.valueOf(line.split(" ")[startIndex + 1])));

            } else if (line.startsWith(sFace)) { //faces

                List<Integer> verts = new ArrayList<>();
                for (int i = 0; i < line.split(" ").length - 1; i++) {
                    verts.add(Integer.valueOf(line.split(" ")[startIndex + i].split(seperator)[0]));
                }
                List<Integer> tex = new ArrayList<>();
                List<Integer> norms = new ArrayList<>();

                if (hasTexture && hasNormals) {
                    for (int i = 0; i < line.split(" ").length - 1; i++) {
                        tex.add(Integer.valueOf(line.split(" ")[startIndex + i].split(seperator)[1]));
                    }

                    for (int i = 0; i < line.split(" ").length - 1; i++) {
                        norms.add(Integer.valueOf(line.split(" ")[startIndex + i].split(seperator)[2]));
                    }
                } else if (hasTexture) {
                    for (int i = 0; i < line.split(" ").length - 1; i++) {
                        tex.add(Integer.valueOf(line.split(" ")[startIndex + i].split(seperator)[1]));
                    }
                } else if (hasNormals) {
                    for (int i = 0; i < line.split(" ").length - 1; i++) {
                        norms.add(Integer.valueOf(line.split(" ")[startIndex + i].split(seperator)[1]));
                    }
                }

                int[] vert = new int[verts.size()];
                for (int i = 0; i < vert.length; i++) {
                    vert[i] = verts.get(i);
                }

                int[] texCoords = null;
                if (tex.size() != 0) {
                    texCoords = new int[tex.size()];
                    for (int i = 0; i < texCoords.length; i++) {
                        texCoords[i] = tex.get(i);
                    }
                }
                int[] norm = null;
                if (norms.size() != 0) {
                    norm = new int[norms.size()];
                    for (int i = 0; i < norm.length; i++) {
                        norm[i] = norms.get(i);
                    }
                }

                model.faces.add(new ObjData.Face(vert, norm, texCoords, verts.size()));
            }
        }
        file.close();
        return model;
    }
}
