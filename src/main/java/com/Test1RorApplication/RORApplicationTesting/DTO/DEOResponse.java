package com.Test1RorApplication.RORApplicationTesting.DTO;

import com.Test1RorApplication.RORApplicationTesting.Model.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DEOResponse {
    List<Users> users;
}
