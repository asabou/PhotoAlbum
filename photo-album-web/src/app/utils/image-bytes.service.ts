import { Injectable } from "@angular/core";

@Injectable()
export class ImageBytesService {
    private readonly imageType: string = 'data:image/jpeg;base64,';
    
    getImageFromBytes(bytes: any) {
        return this.imageType + bytes;
    }

    getBytesFromImage(image: any) {
        return image.split(",")[1];
    }
}