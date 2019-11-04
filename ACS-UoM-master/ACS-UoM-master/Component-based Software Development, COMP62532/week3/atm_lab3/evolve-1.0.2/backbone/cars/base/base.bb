stratum b9773df3-2c13-4129-ab4c-7b52a80654ee/base/
    parent 1482549b-95f1-42f9-bcab-3ff93d3606bf/model/
     is-relaxed
    depends-on backbone
{
    interface e40360de-12e6-4066-88e9-73b9c0513574/IRentalCarDetails/ implementation-class com.intrinsarc.base.IRentalCarDetails
    {
    }

    interface 896e9fa2-7ff8-47c9-97f5-802317422372/IRenterDetails/ implementation-class com.intrinsarc.base.IRenterDetails
    {
    }

    component 7c79a999-065e-42b8-86d3-6e3b29712b6d/RentalCar/
    {
        attributes:
            ea34e9c7-b522-4f84-9c18-f17f3ad6ad7b/model/: String,
            93c61d5a-be66-4bc1-82be-05cc463b7ff7/purchasedWhen/: Date;
        ports:
            27dead0c-f58b-4e1e-858d-fadf1ccbd8a2/car/;
        parts:
            c6d4caa6-cff6-4bee-b245-ea03d785d2ae: 0e74b0d8-1bbf-432c-a88b-7ef1ef55437a/RentalCarDetails/
                slots:
                    b30e9169-a528-459a-ba4e-2df04a5d23db/purchased/ = 93c61d5a-be66-4bc1-82be-05cc463b7ff7/purchasedWhen/
                    f4c11dce-5dc6-4582-b279-bfd5d4093105/model/ = ea34e9c7-b522-4f84-9c18-f17f3ad6ad7b/model/,
            11f33863-3b39-4b6b-88b4-6089e3930e2b: 3a21f5f9-e275-46ab-8c7c-693a444b8c5e/RenterDetails/;
        connectors:
            6c5cd69d-6a71-41a4-a8ef-e906043e695a joins a7306cc0-6095-487e-a923-15f3e986210e/details/@c6d4caa6-cff6-4bee-b245-ea03d785d2ae to 27dead0c-f58b-4e1e-858d-fadf1ccbd8a2/car/,
            309f83d5-6439-4d89-bd65-6e8555ba14f8 joins baa266b2-74e4-4b49-811b-30f7fb5fd8f0/renter/@c6d4caa6-cff6-4bee-b245-ea03d785d2ae to 3a7aa4ff-62d7-48d6-b66b-5804f1e82c76/details/@11f33863-3b39-4b6b-88b4-6089e3930e2b;
    }

    component 0e74b0d8-1bbf-432c-a88b-7ef1ef55437a/RentalCarDetails/ implementation-class com.intrinsarc.base.RentalCarDetails
    {
        attributes:
            f4c11dce-5dc6-4582-b279-bfd5d4093105/model/: String,
            b30e9169-a528-459a-ba4e-2df04a5d23db/purchased/: Date;
        ports:
            a7306cc0-6095-487e-a923-15f3e986210e/details/ provides e40360de-12e6-4066-88e9-73b9c0513574/IRentalCarDetails/,
            baa266b2-74e4-4b49-811b-30f7fb5fd8f0/renter/ requires 896e9fa2-7ff8-47c9-97f5-802317422372/IRenterDetails/;
    }

    component 3a21f5f9-e275-46ab-8c7c-693a444b8c5e/RenterDetails/ implementation-class com.intrinsarc.base.RenterDetails
    {
        attributes:
            4235ae32-b89a-4dea-a99f-a88eca226ea2/renterName/: String = "";
        ports:
            3a7aa4ff-62d7-48d6-b66b-5804f1e82c76/details/ provides 896e9fa2-7ff8-47c9-97f5-802317422372/IRenterDetails/;
    }

    component 01c8e255-9867-4c92-8bc9-513c9955aa72/CarsExample/
    {
        ports:
            daefc8f9-94ca-4e19-ae62-95f2d3bbfdd6/run/;
        parts:
            9f2e80c4-4127-4e65-ac8e-6b99495fe2c3: 68b74864-6163-4364-b17b-e9b9c174ecf4/CarsExampleRunner/,
            7c0ed514-2d8a-40fd-88fa-94d76399811f/sportsCar/: 7c79a999-065e-42b8-86d3-6e3b29712b6d/RentalCar/
                slots:
                    ea34e9c7-b522-4f84-9c18-f17f3ad6ad7b/model/ = "Porsche"
                    93c61d5a-be66-4bc1-82be-05cc463b7ff7/purchasedWhen/ = (105, 10, 25),
            2494335f-0115-44b9-b67a-7a94b7191975/compactCar/: 7c79a999-065e-42b8-86d3-6e3b29712b6d/RentalCar/
                slots:
                    ea34e9c7-b522-4f84-9c18-f17f3ad6ad7b/model/ = "Mini"
                    93c61d5a-be66-4bc1-82be-05cc463b7ff7/purchasedWhen/ = (109, 5, 15);
        connectors:
            d772582d-261d-498b-8afa-02ba74ea08a0 joins 52ba6984-73de-42d4-8e19-489841aeb0ef/run/@9f2e80c4-4127-4e65-ac8e-6b99495fe2c3 to daefc8f9-94ca-4e19-ae62-95f2d3bbfdd6/run/,
            8daadec4-6075-4d41-8211-03e24dfb9a27 joins 27dead0c-f58b-4e1e-858d-fadf1ccbd8a2/car/@7c0ed514-2d8a-40fd-88fa-94d76399811f/sportsCar/ to e2d2945a-da9d-4db4-9391-f2929b98652d/cars/[a]@9f2e80c4-4127-4e65-ac8e-6b99495fe2c3,
            1662d77f-35ff-4116-967e-fbc95309760f joins 27dead0c-f58b-4e1e-858d-fadf1ccbd8a2/car/@2494335f-0115-44b9-b67a-7a94b7191975/compactCar/ to e2d2945a-da9d-4db4-9391-f2929b98652d/cars/[b]@9f2e80c4-4127-4e65-ac8e-6b99495fe2c3;
    }

    component 68b74864-6163-4364-b17b-e9b9c174ecf4/CarsExampleRunner/ has-lifecycle-callbacks implementation-class com.intrinsarc.base.CarsExampleRunner
    {
        ports:
            52ba6984-73de-42d4-8e19-489841aeb0ef/run/ provides IRun,
            e2d2945a-da9d-4db4-9391-f2929b98652d/cars/[0 upto *] requires e40360de-12e6-4066-88e9-73b9c0513574/IRentalCarDetails/;
    }

}

