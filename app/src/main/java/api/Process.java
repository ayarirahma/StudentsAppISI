package api;

/**
 * Created by Geek Hamza on 24/04/2018.
 */

public class Process {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getVersionTag() {
        return versionTag;
    }

    public void setVersionTag(String versionTag) {
        this.versionTag = versionTag;
    }

    public String getHistoryTimeToLive() {
        return historyTimeToLive;
    }

    public void setHistoryTimeToLive(String historyTimeToLive) {
        this.historyTimeToLive = historyTimeToLive;
    }

    public String id,key, category,description,name,version,resource,deploymentId,diagram,suspended,tenantId,versionTag,historyTimeToLive;

}
