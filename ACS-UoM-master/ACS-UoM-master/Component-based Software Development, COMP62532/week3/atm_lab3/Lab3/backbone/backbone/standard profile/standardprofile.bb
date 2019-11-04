stratum standard-profile
    parent backbone
    
    depends-on backbone-profile
{
    component error is-stereotype implementation-class error
    {
        attributes:
            error-description: String;
    }

}

