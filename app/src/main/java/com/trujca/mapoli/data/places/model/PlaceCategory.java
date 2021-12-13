package com.trujca.mapoli.data.places.model;

import com.trujca.mapoli.R;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlaceCategory {
    ARTS_AND_ENTERTAINMENT(10000, R.string.arts_and_entertainment, R.drawable.ic_theater_comedy_24),
    BUSINESS_AND_PROFESSIONAL_SERVICES(11000, R.string.business_and_professional_services, R.drawable.ic_work_24),
    COMMUNITY_AND_GOVERNMENT(12000, R.string.community_and_government, R.drawable.ic_groups_24),
    DINING_AND_DRINKING(13000, R.string.dining_and_drinking, R.drawable.ic_restaurant_24),
    EVENT(14000, R.string.event, R.drawable.ic_event_24),
    HEALTH_AND_MEDICINE(15000, R.string.health_and_medicine, R.drawable.ic_medical_services_24),
    LANDMARKS_AND_OUTDOORS(16000, R.string.landmarks_and_outdoors, R.drawable.ic_landscape_24),
    RETAIL(17000, R.string.retail, R.drawable.ic_store_24),
    SPORTS_AND_RECREATION(18000, R.string.sports_and_recreation, R.drawable.ic_sports_soccer_24),
    TRAVEL_AND_TRANSPORTATION(19000, R.string.travel_and_transportation, R.drawable.ic_flight_24);

    private final Integer placeId;
    private final Integer nameId;
    private final Integer iconId;
}
