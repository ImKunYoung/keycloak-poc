import { IArtist } from 'app/shared/model/artist.model';

export interface IArtistComment {
  id?: number;
  voMember?: number | null;
  content?: string | null;
  artist?: IArtist | null;
}

export const defaultValue: Readonly<IArtistComment> = {};
