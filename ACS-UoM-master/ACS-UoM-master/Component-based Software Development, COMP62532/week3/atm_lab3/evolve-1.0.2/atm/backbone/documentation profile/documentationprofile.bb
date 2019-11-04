stratum documentation-profile
    parent backbone
    
    depends-on backbone-profile
{
    component documentation-top is-stereotype implementation-class documentation-top
    {
        attributes:
            copyrightYears: String,
            documentName: String,
            email: String,
            numberOfSpacesForPadding: String,
            owner: String,
            pageTitlePrefix: String,
            siteIndex: String;
    }

    component documentation-included is-stereotype implementation-class documentation-included
    {
    }

    component documentation-figure is-stereotype implementation-class documentation-figure
    {
    }

    component documentation-see-also is-stereotype implementation-class documentation-see-also
    {
    }

    component documentation-image-gallery is-stereotype implementation-class documentation-image-gallery
    {
    }

}

