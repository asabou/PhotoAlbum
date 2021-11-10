package com.tpjad.project.photoalbumapi.utils;

import com.tpjad.project.photoalbumapi.dao.entity.RoleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ServiceUtils {
   public static List<RoleEntity> convertSetToList(Set<RoleEntity> input) {
       final List<RoleEntity> target = new ArrayList<>(input);
       return target;
   }
}
