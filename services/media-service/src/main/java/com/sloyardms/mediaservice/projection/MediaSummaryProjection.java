package com.sloyardms.mediaservice.projection;

import java.util.UUID;

public interface MediaSummaryProjection {

    UUID getId();

    String getMainTitle();

    String getSummary();

    String getThumbnailUrl();

}
