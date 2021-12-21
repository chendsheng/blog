package com.onestep.service.impl;

import com.onestep.dao.AdminMapper;
import com.onestep.entity.Admin;
import com.onestep.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {
  @Resource
  private AdminMapper adminMapper;

  @Override
  public int count() {
    return adminMapper.count();
  }

  @Override
  public String selectRole(String name) {
    return adminMapper.selectRole(name);
  }

  @Override
  public Admin selectAdmin(String name) {
    return adminMapper.selectAdmin(name);
  }

  @Override
  public List<Admin> selectAdmins() {
    return adminMapper.selectAdmins();
  }


  @Override
  public int insertAdmin(Admin admin) {
    if (adminMapper.selectAdmin(admin.getName()) == null) {
      return adminMapper.insertAdmin(admin);
    } else {
      return 0;
    }
  }

  @Override
  public int updateAdminbyId(Admin admin) {
    Admin adminSelected = adminMapper.selectAdmin(admin.getName());
    if (adminSelected == null || adminSelected.getId().equals(admin.getId())) {
      return adminMapper.updateAdminbyId(admin);
    } else {
      return 0;
    }
  }

  @Override
  public int batchDeleteAdminbyIds(Integer[] ids) {
    if (ids.length>0){
      return adminMapper.batchDeleteAdminbyIds(ids);
    }else {
      return 0;
    }
  }
}
