package com.ncepu.easygift.result;


public enum REnum {
    SUCCESS(20000,  "成功" , true),
    ERROR(20001, "失败", false),

    OperationFailure(10009, "请求操作失败", false),

    ParamError(40001, "获取请求参数失败", false),
    LoginError(40002, "登录已过期", false),
    AuthorityError(40003, "请求不合权限", false),
    RoleError(40004, "角色错误", false),

    DataExistError(50001, "数据已存在", false),
    NoDataError(50002, "信息不存在", false),

    InternalError(60001, "服务器错误", false)
    ;

    private Integer code;
    private String message;
    private boolean success;

    REnum() {
    }

    REnum(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
