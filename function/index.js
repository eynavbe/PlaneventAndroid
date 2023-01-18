const functions = require("firebase-functions");
// firebase deploy --only functions
// firebase functions:shell
// The Firebase Admin SDK to access Firestore.
const admin = require('firebase-admin');
admin.initializeApp();

// readDataFromFirebase({uid:"", collection1:"hall", doc1: "חגיגנ", collection2: "events"})

exports.readDataFromFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        let docRef = await admin.firestore().collection(collection1).get();
        const doc1 = data.doc1;
        console.log(doc1);
        if(doc1 != null){
            const collection2 = data.collection2;
            console.log(collection2);
            if(collection2 != null){
                docRef = await admin.firestore().collection(collection1).doc(doc1).collection(collection2).get();
            }
            const doc2 = data.doc2;
                if(doc2 != null){
                    const collection3 = data.collection3;
                    if(collection3 != null){
                        docRef = await admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2).collection(collection3).get();
                    }
                    const doc3 = data.doc3;
                if(doc3 != null){
                    const collection4 = data.collection4;
                    if(collection4 != null){
                    docRef = await admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2).collection(collection3).doc(doc3).collection(collection4).get();
                    }
                }
            } 
        }
        let dataArray = []
        if(docRef.docs){
            docRef.docs.forEach((doc) =>{
                console.log(doc.data());
                dataArray.push(doc.data());
            })
        }
        return dataArray;
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});



exports.deleteDataFromFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        const doc1 = data.doc1;
        let docRef = admin.firestore().collection(collection1).doc(doc1);
        const collection2 = data.collection2;
        if(collection2 != null){
            const doc2 = data.doc2;
            if(doc2 != null){

                docRef = admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2);
            }
            const collection3 = data.collection3;
            if(collection3 != null){
                const doc3 = data.doc3;
                if(doc3 != null){
                    docRef = admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2).collection(collection3).doc(doc3);
                }
                const collection4 = data.collection4;
                if(collection4 != null){
                    const doc4 = data.doc4;
                    if(doc4 != null){
                    docRef = admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2).collection(collection3).doc(doc3).collection(collection4).doc(doc4);
                    }
                }
          
            }
        }
        const writeResult = await docRef.delete({
        });
        return {delete: "delete"};
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});


exports.writeUserToFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        const doc1 = data.doc1;
        const emailClient = data.emailClient;
        let docRef = admin.firestore().collection(collection1).doc(doc1);
        const writeResult = await docRef.set({
            emailClient: emailClient,
        });
        return {emailClient: data.emailClient,uid:context.auth.uid};
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});

exports.writeClientChoiceTypeToFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        const doc1 = data.doc1;
        const collection2 = data.collection2;
        const doc2 = data.doc2;
        const collection3 = data.collection3;
        const doc3 = data.doc3;
        const name = data.name;
        const price = data.price;
        const image = data.image;
        const inPrice = data.inPrice;
        const priceClient = data.priceClient;
        const chooseThis = data.chooseThis;
        let docRef = admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2).collection(collection3).doc(doc3);
        const writeResult = await docRef.set({
            name: name,
            price: price,
            image: image,
            inPrice: inPrice,
            priceClient: priceClient,
            chooseThis: chooseThis,
        });
        return {name: name,price: price,image: image,inPrice: inPrice,priceClient: priceClient, chooseThis: chooseThis,uid:context.auth.uid};
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});



exports.writeHallProductToFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        const doc1 = data.doc1;
        const collection2 = data.collection2;
        const doc2 = data.doc2;
        const name = data.name;
        const price = data.price;
        const image = data.image;
        const inPrice = data.inPrice;
        const priceClient = data.priceClient;
        const chooseThis = data.chooseThis;
        let docRef = admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2);
        const writeResult = await docRef.set({
            name: name,
            price: price,
            image: image,
            inPrice: inPrice,
            priceClient: priceClient,
            chooseThis: chooseThis,
        });
        return {name: name,price: price,image: image,inPrice: inPrice,priceClient: priceClient, chooseThis: chooseThis,uid:context.auth.uid};
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});



exports.writeCountHallEditToFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        const doc1 = data.doc1;
        const collection2 = data.collection2;
        const doc2 = data.doc2;
        const chooseCountAll = data.chooseCountAll;
        const chooseFromAll = data.chooseFromAll;
        let docRef = admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2);
        const writeResult = await docRef.set({
            chooseCountAll: chooseCountAll,
            chooseFromAll: chooseFromAll,
        });
        return {chooseCountAll: chooseCountAll,chooseFromAll:chooseFromAll,uid:context.auth.uid};
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});


exports.writeHallToFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        const doc1 = data.doc1;
        const hallName = data.hallName;
        const hallArea = data.hallArea;
        const maxHallPeople = data.maxHallPeople;
        const phoneNum = data.phoneNum;
        const email = data.email;
        let docRef = admin.firestore().collection(collection1).doc(doc1);
        const writeResult = await docRef.set({
            hallName: hallName,
            hallArea: hallArea,
            maxHallPeople: maxHallPeople,
            phoneNum: phoneNum,
            email: email,
        });
        return {hallName: hallName,hallArea:hallArea, maxHallPeople:maxHallPeople,phoneNum:phoneNum,email:email, uid:context.auth.uid};
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});


exports.writeEventToFirebase = functions.https.onCall(async (data, context) => {
    console.log(data);
    const uid = checkAuth(data,context);
    if(uid != null){
        const collection1 = data.collection1;
        const doc1 = data.doc1;
        const collection2 = data.collection2;
        const doc2 = data.doc2;
        const emailClient1 = data.emailClient1;
        const emailClient2 = data.emailClient2;
        const countInvited = data.countInvited;
        const price = data.price;
        const typeEvent = data.typeEvent;
        const dateEvent = data.dateEvent;
        const lastNameEvent = data.lastNameEvent;
        const hourEvent = data.hourEvent;
        let docRef = admin.firestore().collection(collection1).doc(doc1).collection(collection2).doc(doc2);
        const writeResult = await docRef.set({
            emailClient1: emailClient1,
            emailClient2: emailClient2,
            countInvited: countInvited,
            price: price,
            typeEvent: typeEvent,
            dateEvent: dateEvent,
            lastNameEvent: lastNameEvent,
            hourEvent: hourEvent,
        });
        return {emailClient1: emailClient1,emailClient2:emailClient2, countInvited:countInvited,price:price,typeEvent:typeEvent,dateEvent:dateEvent,lastNameEvent:lastNameEvent,hourEvent:hourEvent, uid:context.auth.uid};
    }
    else{
        throw new functions.https.HttpsError(
            "unauthenticated",
            "user no login"
        );
    }
    return null;
});

const checkAuth = (data, context) =>{
    if(context.auth){
        return context.auth.uid;
    }else{
        console.log(data.uid);

        if(data.uid){
            return data.uid;
        }
    }
    
}
