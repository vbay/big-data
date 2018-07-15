package org.busuanzi.spark;

/**
 * @author wangxiaolei(王小雷)
 * @since 2018/7/11
 */
public class TopNURL {
    String username;
    String projects;
    Integer star_number;
    String comment;

    public Integer getStar_number() {
        return star_number;
    }

    public void setStar_number(Integer star_number) {
        this.star_number = star_number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
